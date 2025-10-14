package dev.wekend.housingtoolbox

import dev.wekend.housingtoolbox.feature.ChatInput
import dev.wekend.housingtoolbox.keybind.KeyBindings
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.client.MinecraftClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object HousingToolbox : ClientModInitializer {
    val MOD_ID = "housingtoolbox"
    val LOGGER: Logger = LoggerFactory.getLogger("Housing Toolbox")
    val VERSION = /*$ mod_version*/ "0.0.1"
    val MINECRAFT = /*$ minecraft*/ "1.21.9"
    val MC: MinecraftClient
        get() = MinecraftClient.getInstance()

    override fun onInitializeClient() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Loaded Housing Toolbox v$VERSION for Minecraft $MINECRAFT.");

        ClientReceiveMessageEvents.GAME.register { message, _ -> ChatInput.onGameMessage(message) }

        KeyBindings.init()
    }
}