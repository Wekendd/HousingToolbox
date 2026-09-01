plugins {
    kotlin("jvm") version "2.2.10"
    id("dev.kikugie.loom-back-compat")
}

version = "${property("mod.version")}+${stonecutter.current.version}"
base.archivesName = property("mod.id") as String

val requiredJava: JavaVersion = when {
    stonecutter.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    stonecutter.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    stonecutter.current.parsed >= "1.18" -> JavaVersion.VERSION_17
    stonecutter.current.parsed >= "1.17" -> JavaVersion.VERSION_16
    else -> JavaVersion.VERSION_1_8
}

val kotlinJvmTarget = requiredJava.majorVersion.let { javaVersion ->
    when {
        javaVersion >= "25" -> "24"
        else -> javaVersion
    }
}


repositories {
    /**
     * Restricts dependency search of the given [groups] to the [maven URL][url],
     * improving the setup speed.
     */
    fun strictMaven(url: String, alias: String, vararg groups: String) = exclusiveContent {
        forRepository { maven(url) { name = alias } }
        filter { groups.forEach(::includeGroup) }
    }

    strictMaven("https://api.modrinth.com/maven", "Modrinth", "maven.modrinth")
    strictMaven("https://maven.terraformersmc.com/", "Terraformers")
    strictMaven("https://maven.isxander.dev/releases", "Xander Maven")
    strictMaven("https://maven.siphalor.de/", "Siphalor's Maven")

    strictMaven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1", "DevAuth")
}

dependencies {
    /**
     * Fetches only the required Fabric API modules to not waste time downloading all of them for each version.
     * @see <a href="https://github.com/FabricMC/fabric">List of Fabric API modules</a>
     */
    fun fapi(vararg modules: String) {
        for (it in modules) modImplementation(fabricApi.module(it, property("deps.fabric_api") as String))
    }

    minecraft("com.mojang:minecraft:${stonecutter.current.version}")
    loomx.applyMojangMappings()

    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")

    modImplementation("net.fabricmc:fabric-language-kotlin:${property("deps.fabric_language_kotlin")}")
    modImplementation("com.terraformersmc:modmenu:${property("deps.mod_menu")}")
    modImplementation("dev.isxander:yet-another-config-lib:${property("deps.yacl")}")
    include(modImplementation("de.siphalor.amecs.amecs-key-modifiers:${property("deps.amecs_key_modifiers")}")!!)
    include(modImplementation("de.siphalor.amecs.amecs-priority-key-mappings:${property("deps.amecs_priority_key_mappings")}")!!)

    if (stonecutter.current.parsed >= "26.1") {
        fapi(
            "fabric-lifecycle-events-v1",
            "fabric-resource-loader-v0",
            "fabric-content-registries-v0",
            "fabric-message-api-v1",
            "fabric-key-mapping-api-v1"
        )
    } else {
        fapi(
            "fabric-lifecycle-events-v1",
            "fabric-resource-loader-v0",
            "fabric-content-registries-v0",
            "fabric-message-api-v1",
            "fabric-key-binding-api-v1"
        )
    }

    modRuntimeOnly("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
    modRuntimeOnly("me.djtheredstoner:DevAuth-fabric:1.2.2")
}

loom {
    decompilerOptions.named("vineflower") {
        options.put("mark-corresponding-synthetics", "1") // Adds names to lambdas - useful for mixins
    }

    runConfigs.all {
        ideConfigGenerated(true)
        vmArgs("-Dmixin.debug.export=true") // Exports transformed classes for debugging
        runDir = "../../run" // Shares the run directory between versions
    }
}

java {
    withSourcesJar()
    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(kotlinJvmTarget))
    }
}

tasks {
    processResources {
        val props = mapOf(
            "id" to project.property("mod.id"),
            "name" to project.property("mod.name"),
            "version" to project.property("mod.version"),
            "minecraft" to project.property("mod.mc_compat"),
            "fabric_loader" to project.property("deps.fabric_loader"),
            "fabric_language_kotlin" to project.property("deps.fabric_language_kotlin"),
            "fabric_api" to project.property("deps.fabric_api"),
            "yacl" to project.property("deps.yacl")
        )

        filesMatching("fabric.mod.json") { expand(props) }
    }

    // Builds the version into a shared folder in `build/libs/${mod version}/`
    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds mod jars and copies results to `build/libs/{mod version}/`"

        inputs.property("version", project.property("mod.version"))
        // loomx.mod(Sources)Jar returns the jar task for the applied loom variant
        from(loomx.modJar.flatMap { it.archiveFile }, loomx.modSourcesJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
    }
}
