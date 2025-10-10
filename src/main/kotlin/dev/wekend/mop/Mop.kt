package dev.wekend.mop

import dev.wekend.mop.config.MopSettings
import dev.wekend.mop.events.ChatEvents
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
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

        LOGGER.info("Loaded Mop v$VERSION for Minecraft $MINECRAFT.");

        ClientReceiveMessageEvents.GAME.register { message, _ -> ChatEvents.onGameMessage(message) }
    }
}