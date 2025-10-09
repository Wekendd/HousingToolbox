package dev.wekend.mop

import dev.wekend.mop.config.MopSettings
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

object Mop : ClientModInitializer {
    val LOGGER: Logger = LoggerFactory.getLogger("Mop")
    val VERSION = /*$ mod_version*/ "0.0.1"
    val MINECRAFT = /*$ minecraft*/ "1.21.9"

    override fun onInitializeClient() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello World!");

        MopSettings



    }

    fun id(namespace: String, path: String): Identifier =
        Identifier.of(namespace, path)
}