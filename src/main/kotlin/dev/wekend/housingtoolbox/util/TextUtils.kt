package dev.wekend.housingtoolbox.util

import dev.wekend.housingtoolbox.feature.ChatInput
import net.minecraft.block.entity.SignText
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ingame.AnvilScreen
import net.minecraft.item.Items
import net.minecraft.text.Text
import net.minecraft.text.TextColor
import net.minecraft.util.Formatting
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletableFuture.*
import java.util.concurrent.TimeUnit


object TextUtils {
    private val VALID_SIGN_TEMPLATES: List<List<String>> = listOf(
        listOf("^^^^^^", "Enter your", "search query!"),
        listOf("^^^^^^", "Enter a word", "to filter on!")
    )

    fun isInputSign(text: SignText): Boolean {
        val lines: List<String?> = text.getMessages(false).map { line ->
            if (line.siblings.isEmpty()) null else line.siblings?.get(0)?.literalString
        }.subList(1, 4)

        return VALID_SIGN_TEMPLATES.stream().anyMatch { o: List<String> -> lines == o }
    }

    fun isAnvilInput(name: String?): Boolean {
        val container = MinecraftClient.getInstance().currentScreen as? AnvilScreen
            ?: return false
        if (name == null) {
            return true
        } else {
            return container.title.string.contains(name)
        }
    }

    fun convertTextToString(text: Text): String {
        return text.siblings.joinToString("") {
            var part = it.string
            val style = it.style
            if (style.color != null) {
                val color: TextColor = style.color!!
                for (format in Formatting.entries) {
                    if (color.rgb == format.colorValue) {
                        part = (format.toString() + part).replace("§", "&")
                    }
                }
            }
            part
        }
    }

    fun sendMessage(message: String) {
        val player = MinecraftClient.getInstance().player
        player?.networkHandler?.sendChatMessage(message)
    }

    fun sendMessage(message: String, delayMs: Long) {
        runAsync({
            sendMessage(message)
        }, delayedExecutor(delayMs, TimeUnit.MILLISECONDS))
    }

    fun input(message: String) {
        if (isAnvilInput(null)) {
            val anvil = MinecraftClient.getInstance().currentScreen as AnvilScreen
            anvil.screenHandler.setNewItemName(message)
            MenuUtils.clickMenuSlotWithDelay(100L, MenuUtils.MenuSlot(Items.PAPER, message))
        } else {
            sendMessage(message)
        }
    }

    fun input(message: String, delayMs: Long) {
        runAsync({
            input(message)
        }, delayedExecutor(delayMs, TimeUnit.MILLISECONDS))
    }

    fun previousTextOfInput(delayMs: Long): CompletableFuture<String?> {
        return supplyAsync<String>({
            if (isAnvilInput(null)) {
                val anvil = MinecraftClient.getInstance().currentScreen as AnvilScreen
                MinecraftClient.getInstance().setScreen(null)
                anvil.screenHandler.slots[0].stack.name.string
            } else {
                if (ChatInput.previousInput != null) {
                    CommandUtils.runCommand("chatinput cancel")

                    ChatInput.previousInput
                } else {
                    CommandUtils.runCommand("chatinput cancel")
                    null
                }
            }
        }, delayedExecutor(delayMs, TimeUnit.MILLISECONDS))
    }
}