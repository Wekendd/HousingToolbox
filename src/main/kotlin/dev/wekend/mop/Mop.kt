package dev.wekend.mop

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Mop : ModInitializer {
    val LOGGER: Logger = LoggerFactory.getLogger("Mop")
    const val VERSION = /*$ mod_version*/ "0.0.1"
    const val MINECRAFT = /*$ minecraft*/ "1.21.9"

    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello World!");
    }

    fun id(namespace: String, path: String): Identifier {
        return Identifier.of(namespace, path)
    }
}