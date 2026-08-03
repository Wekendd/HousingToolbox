package dev.wekend.housingtoolbox.keybind

import dev.wekend.housingtoolbox.HousingToolbox.MC
import dev.wekend.housingtoolbox.keybind.KeyBindings.KeySpec
import dev.wekend.housingtoolbox.util.currentScreen
import net.minecraft.client.gui.screens.inventory.ContainerScreen
import org.lwjgl.glfw.GLFW

object CommandShortcuts {

    private fun runIfNoScreenOrContainer(cmd: String): Boolean {
        val s = MC.currentScreen

        if (s == null || s is ContainerScreen) {
            MC.connection?.sendCommand(cmd)
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