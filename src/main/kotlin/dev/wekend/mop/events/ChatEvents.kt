package dev.wekend.mop.events

import dev.wekend.mop.Mop
import dev.wekend.mop.config.MopSettings
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ChatScreen
import net.minecraft.text.ClickEvent
import net.minecraft.text.PlainTextContent
import net.minecraft.text.Text

object ChatEvents {
    fun onGameMessage(message: Text) {
        if (!MopSettings.chatAutoOpen.get()) return

        val inputRequested = message.siblings.firstOrNull { sibling ->
            (sibling.content as? PlainTextContent.Literal)?.string() == " [CANCEL]"
        } != null
        if (!inputRequested) return

        val prevInput = message.siblings.firstNotNullOfOrNull { sibling ->
            if ((sibling.content as? PlainTextContent.Literal)?.string() == " [PREVIOUS]") {
                (sibling.style.clickEvent as? ClickEvent.SuggestCommand)?.command
            } else null
        }

        val client = MinecraftClient.getInstance()

        // Ignore server's close screen packet for 2 ticks
        IgnoreCloseScreens.startIgnoring(2)
        ClientTickEvents.END_WORLD_TICK.register { IgnoreCloseScreens.tick() }

        val chatScreen = if (MopSettings.chatIncludePrevious.get() && prevInput != null) {
            ChatScreen(prevInput, true)
        } else {
            ChatScreen("", false)
        }

        client.setScreen(chatScreen)
    }

}

object IgnoreCloseScreens {
    private var ignoreTicks = 0

    fun tick() {
        if (ignoreTicks > 0) ignoreTicks--
    }

    fun startIgnoring(ticks: Int) {
        ignoreTicks = ticks
    }

    fun shouldIgnore(): Boolean = ignoreTicks > 0
}