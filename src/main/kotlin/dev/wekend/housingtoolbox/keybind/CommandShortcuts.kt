package dev.wekend.housingtoolbox.keybind

import dev.wekend.housingtoolbox.HousingToolbox.MC
import dev.wekend.housingtoolbox.keybind.KeyBindings.KeySpec
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen
import org.lwjgl.glfw.GLFW

object CommandShortcuts {

    private inline fun runIfNoScreenOrContainer(cmd: String): Boolean {
        val s = MC.currentScreen
        if (s == null || s is GenericContainerScreen) {
            MC.networkHandler?.sendChatCommand(cmd)
            return true
        }
        return false
    }

    fun init() {
        KeyBindings.registerAll(
            listOf(
                KeySpec(
                    id = "menu",
                    key = GLFW.GLFW_KEY_UNKNOWN,
                    onPress = { runIfNoScreenOrContainer("menu") }
                ),
                KeySpec(
                    id = "functions",
                    key = GLFW.GLFW_KEY_UNKNOWN,
                    onPress = { runIfNoScreenOrContainer("functions") }
                ),
                KeySpec(
                    id = "events",
                    key = GLFW.GLFW_KEY_UNKNOWN,
                    onPress = { runIfNoScreenOrContainer("events") }
                )
            )
        )
    }

}