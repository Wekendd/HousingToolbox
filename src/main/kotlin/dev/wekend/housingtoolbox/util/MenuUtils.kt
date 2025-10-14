package dev.wekend.housingtoolbox.util

import dev.wekend.housingtoolbox.HousingToolbox.MC
import dev.wekend.housingtoolbox.keybind.KeyBindings
import dev.wekend.housingtoolbox.keybind.KeyNav
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen
import net.minecraft.component.DataComponentTypes
import net.minecraft.item.Item
import net.minecraft.screen.slot.SlotActionType

object MenuUtils {

    private inline fun withContainer(block: (GenericContainerScreen) -> Boolean): Boolean {
        val gui = MC.currentScreen as? GenericContainerScreen ?: return false
        return block(gui)
    }

    private fun findSlot(
        gui: GenericContainerScreen,
        item: Item,
        name: String
    ) = gui.screenHandler.slots.firstOrNull {
        it.stack.item == item &&
                it.stack.components.get(DataComponentTypes.CUSTOM_NAME)?.string == name
    }

    fun click(gui: GenericContainerScreen, slot: Int) {
        MC.interactionManager?.clickSlot(
            gui.screenHandler.syncId,
            slot,
            0,
            SlotActionType.THROW,
            MC.player
        )
    }

    fun clickMenuSlot(vararg targets: KeyNav.MenuSlot): Boolean =
        withContainer { gui ->
            val slot = targets.firstNotNullOfOrNull { findSlot(gui, it.item, it.label) }
            if (slot != null) {
                click(gui, slot.id)
                true
            } else {
                false
            }
        }

}