package dev.wekend.housingtoolbox.keybind

import com.mojang.blaze3d.platform.InputConstants
import de.siphalor.amecs.key_modifiers.api.AmecsKeyModifierCombination
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

    private fun runIfNoScreen(cmd: String): Boolean {
        val s = MC.currentScreen

        if (s == null) {
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
                ),
                KeySpec(
                    id = "protool-copy",
                    key = InputConstants.KEY_C,
                    modifiers = AmecsKeyModifierCombination(false, true, false),
                    onPress = { runIfNoScreen("copy") }
                ),
                KeySpec(
                    id = "protool-paste",
                    key = InputConstants.KEY_V,
                    modifiers = AmecsKeyModifierCombination(false, true, false),
                    onPress = { runIfNoScreen("paste") }
                ),
                KeySpec(
                    id = "protool-undo",
                    key = InputConstants.KEY_Z,
                    modifiers = AmecsKeyModifierCombination(false, true, false),
                    onPress = { runIfNoScreen("undo") }
                )
            )
        )
    }

}