package dev.wekend.housingtoolbox.util

import dev.wekend.housingtoolbox.HousingToolbox.MC
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import net.minecraft.client.gui.screens.inventory.ContainerScreen
import net.minecraft.core.component.DataComponents
import net.minecraft.network.HashedStack
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket
import net.minecraft.world.item.Item

object MenuUtils {

    data class Target(val menuSlot: MenuSlot, val button: Int = 0)
    data class MenuSlot(val item: Item, val label: String)

    private inline fun withContainer(block: (ContainerScreen) -> Boolean): Boolean {
        val gui = MC.currentScreen as? ContainerScreen ?: return false
        return block(gui)
    }

    private fun findSlot(gui: ContainerScreen, item: Item, name: String) =
        gui.menu.slots.firstOrNull {
            it.item.item == item &&
            it.item.components.get(DataComponents.CUSTOM_NAME)?.string == name
        }

    fun click(gui: ContainerScreen, slot: Int, button: Int = 0) {
        val pkt = ServerboundContainerClickPacket(
            gui.menu.containerId,
            gui.menu.stateId,
            slot.toShort(),
            button.toByte(),
            ClickAction.PICKUP,
            Int2ObjectOpenHashMap(),
            HashedStack.EMPTY
        )
        MC.connection?.send(pkt)
    }


    fun clickMenuSlot(vararg slots: MenuSlot): Boolean =
        clickMenuTargets(*slots.map { Target(it) }.toTypedArray())

    fun clickMenuTargets(vararg attempts: Target): Boolean =
        withContainer { gui ->
            val match = attempts.firstNotNullOfOrNull {
                val slot = findSlot(gui, it.menuSlot.item, it.menuSlot.label)
                if (slot != null) it to slot else null
            } ?: return@withContainer false
            click(gui, match.second.index, match.first.button)
            true
        }
}