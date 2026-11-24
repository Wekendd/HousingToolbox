package dev.wekend.housingtoolbox

import dev.wekend.housingtoolbox.feature.ChatInput
import dev.wekend.housingtoolbox.keybind.KeyBindings
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.client.Minecraft
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object HousingToolbox : ClientModInitializer {
    const val MOD_ID = "housingtoolbox"
    val LOGGER: Logger = LoggerFactory.getLogger("Housing Toolbox")
    const val VERSION = /*$ mod_version*/ "0.3.1";
    const val MINECRAFT = /*$ minecraft*/ "26.3";
    val MC: Minecraft
        get() = Minecraft.getInstance()

    override fun onInitializeClient() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Loaded Housing Toolbox v$VERSION for Minecraft $MINECRAFT.")

        ClientReceiveMessageEvents.GAME.register { message, _ -> ChatInput.onGameMessage(message) }

        KeyBindings.init()
    }
}