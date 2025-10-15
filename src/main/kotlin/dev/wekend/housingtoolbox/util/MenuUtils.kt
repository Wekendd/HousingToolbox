package dev.wekend.housingtoolbox.util

import dev.wekend.housingtoolbox.HousingToolbox.MC
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen
import net.minecraft.component.DataComponentTypes
import net.minecraft.item.Item
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket
import net.minecraft.screen.slot.SlotActionType
import net.minecraft.screen.sync.ItemStackHash

object MenuUtils {

    data class Target(val menuSlot: MenuSlot, val button: Int = 0)
    data class MenuSlot(val item: Item, val label: String)

    private inline fun withContainer(block: (GenericContainerScreen) -> Boolean): Boolean {
        val gui = MC.currentScreen as? GenericContainerScreen ?: return false
        return block(gui)
    }

    private fun findSlot(gui: GenericContainerScreen, item: Item, name: String) =
        gui.screenHandler.slots.firstOrNull {
            it.stack.item == item &&
            it.stack.components.get(DataComponentTypes.CUSTOM_NAME)?.string == name
        }

    fun click(gui: GenericContainerScreen, slot: Int, button: Int = 0) {
        val pkt = ClickSlotC2SPacket(
            gui.screenHandler.syncId,
            gui.screenHandler.revision,
            slot.toShort(),
            button.toByte(),
            SlotActionType.PICKUP,
            Int2ObjectOpenHashMap(),
            ItemStackHash.EMPTY
        )
        MC.networkHandler?.sendPacket(pkt)
    }


    fun clickMenuSlot(vararg slots: MenuSlot): Boolean =
        clickMenuTargets(*slots.map { Target(it) }.toTypedArray())

    fun clickMenuTargets(vararg attempts: Target): Boolean =
        withContainer { gui ->
            val match = attempts.firstNotNullOfOrNull {
                val slot = findSlot(gui, it.menuSlot.item, it.menuSlot.label)
                if (slot != null) it to slot else null
            } ?: return@withContainer false
            click(gui, match.second.id, match.first.button)
            true
        }
}