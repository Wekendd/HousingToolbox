package dev.wekend.housingtoolbox.feature

import dev.wekend.housingtoolbox.config.HousingToolboxSettings
import dev.wekend.housingtoolbox.util.currentScreen
import dev.wekend.housingtoolbox.util.registerClientEndTick
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.ChatScreen
import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.contents.PlainTextContents
import net.minecraft.network.chat.Component

object ChatInput {

    fun onGameMessage(message: Component) {
        if (!HousingToolboxSettings.chatAutoOpen.get()) return

        val inputRequested = message.siblings.firstOrNull { sibling ->
            (sibling.contents as? PlainTextContents.LiteralContents)?.text() == " [CANCEL]"
        } != null
        if (!inputRequested) return

        val prevInput = message.siblings.firstNotNullOfOrNull { sibling ->
            if ((sibling.contents as? PlainTextContents.LiteralContents)?.text() == " [PREVIOUS]") {
                (sibling.style.clickEvent as? ClickEvent.SuggestCommand)?.command
            } else null
        }

        val client = Minecraft.getInstance()

        // Ignore server's close screen packet for 2 ticks
        IgnoreCloseScreens.startIgnoring(2)
        registerClientEndTick { IgnoreCloseScreens.tick() }

        val chatScreen = if (HousingToolboxSettings.chatIncludePrevious.get() && prevInput != null) {
            ChatScreen(prevInput, true)
        } else {
            ChatScreen("", false)
        }

        client.currentScreen = chatScreen
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

}