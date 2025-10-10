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

        val prevInput: String? = message.siblings
            .firstNotNullOfOrNull { sibling ->
                val isPrevious = (sibling.content as? PlainTextContent.Literal)?.string() == " [PREVIOUS]"
                if (isPrevious) (sibling.style.clickEvent as? ClickEvent.SuggestCommand)?.command
                else null
            }
        if (prevInput == null) return

        val client = MinecraftClient.getInstance()

        // Set up tick window to ignore server's close screen packet
        IgnoreCloseScreens.startIgnoring(2)
        ClientTickEvents.END_WORLD_TICK.register { IgnoreCloseScreens.tick() }

        val chatScreen = if (
            MopSettings.chatIncludePrevious.get()
        ) {
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