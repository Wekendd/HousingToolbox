package dev.wekend.mop.events

import dev.wekend.mop.Mop
import dev.wekend.mop.config.MopSettings
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.hud.ChatHud
import net.minecraft.client.gui.screen.ChatScreen
import net.minecraft.text.ClickEvent
import net.minecraft.text.PlainTextContent
import net.minecraft.text.Text

object ChatEvents {

    fun onGameMessage(message: Text) {
        if (!MopSettings.Companion.chatAutoOpen.get()) return

        val prevInput: String? = message.siblings
            .firstNotNullOfOrNull { sibling ->
                val isPrevious = (sibling.content as? PlainTextContent.Literal)?.string() == " [PREVIOUS]"
                if (isPrevious) (sibling.style.clickEvent as? ClickEvent.SuggestCommand)?.command
                else null
            }

        if (prevInput == null) return
        Mop.LOGGER.info("prevInput: $prevInput")

        // open chat window with previous input
        val client = MinecraftClient.getInstance()
        client.setScreen(ChatScreen(prevInput, false))
    }

}