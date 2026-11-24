package dev.wekend.housingtoolbox.keybind

import com.mojang.blaze3d.platform.InputConstants
import dev.wekend.housingtoolbox.HousingToolbox.MC
import dev.wekend.housingtoolbox.keybind.KeyBindings.KeySpec
import dev.wekend.housingtoolbox.util.currentScreen
import net.minecraft.client.gui.screens.inventory.ContainerScreen

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
                    key = InputConstants.UNKNOWN.value,
                    onPress = { runIfNoScreenOrContainer("menu") }
                ),
                KeySpec(
                    id = "functions",
                    key = InputConstants.UNKNOWN.value,
                    onPress = { runIfNoScreenOrContainer("functions") }
                ),
                KeySpec(
                    id = "events",
                    key = InputConstants.UNKNOWN.value,
                    onPress = { runIfNoScreenOrContainer("events") }
                )
            )
        )
    }

}