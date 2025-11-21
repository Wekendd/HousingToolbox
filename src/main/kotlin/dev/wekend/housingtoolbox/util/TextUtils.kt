package dev.wekend.housingtoolbox.util

import com.github.shynixn.mccoroutine.fabric.launch
import dev.wekend.housingtoolbox.HousingToolbox
import dev.wekend.housingtoolbox.HousingToolbox.MC
import dev.wekend.housingtoolbox.feature.ChatInput
import kotlinx.coroutines.delay
import net.minecraft.block.entity.SignText
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ChatScreen
import net.minecraft.client.gui.screen.ingame.AnvilScreen
import net.minecraft.network.packet.c2s.play.RenameItemC2SPacket
import net.minecraft.screen.AnvilScreenHandler
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
        val container = MC.currentScreen as? AnvilScreen
            ?: return false
        if (name == null) {
            return true
        } else {
            return container.title.string.contains(name)
        }
    }

    fun convertTextToString(text: Text, colors: Boolean = true): String {
        return text.siblings.joinToString("") {
            var part = it.string
            val style = it.style
            if (style.color != null && colors) {
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
        MC.networkHandler?.sendChatMessage(message)
    }

    fun sendMessage(message: String, delayMs: Long) {
        runAsync({
            sendMessage(message)
        }, delayedExecutor(delayMs, TimeUnit.MILLISECONDS))
    }

    suspend fun input(message: String) {
        val screen = MenuUtils.onOpen(null, AnvilScreen::class, ChatScreen::class)
        if (screen is AnvilScreen) {
            if (screen.screenHandler.setNewItemName(message)) {
                MC.networkHandler?.sendPacket(RenameItemC2SPacket(message))
            }
            MenuUtils.delayClick(screen, 2, 0, 100L)
        } else if (screen is ChatScreen) {
            sendMessage(message)
        }
    }

    suspend fun input(message: String, delayMs: Long) {
        delay(delayMs)
        input(message)
        delay(200)
    }
}