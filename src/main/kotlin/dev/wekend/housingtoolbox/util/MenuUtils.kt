package dev.wekend.housingtoolbox.util

import dev.wekend.housingtoolbox.HousingToolbox.MC
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import kotlinx.coroutines.delay
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen
import net.minecraft.component.DataComponentTypes
import net.minecraft.item.Item
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket
import net.minecraft.screen.slot.SlotActionType
import net.minecraft.screen.sync.ItemStackHash
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

object MenuUtils {

    data class Target(val menuSlot: MenuSlot, val button: Int = 0)
    data class MenuSlot(val item: Item?, val label: String?)

    private inline fun withContainer(block: (GenericContainerScreen) -> Boolean): Boolean {
        val gui = MC.currentScreen as? GenericContainerScreen ?: return false
        return block(gui)
    }

    fun findSlot(gui: GenericContainerScreen, menuSlot: MenuSlot) =
        findSlot(gui, menuSlot.item, menuSlot.label)

    fun findSlot(gui: GenericContainerScreen, item: Item?, name: String?) =
        if (name == null && item == null) {
            gui.screenHandler.slots.firstOrNull()
        } else if (name == null) {
            gui.screenHandler.slots.firstOrNull { it.stack.item == item }
        } else if (item == null) {
            gui.screenHandler.slots.firstOrNull {
                it.stack.components.get(DataComponentTypes.CUSTOM_NAME)?.string == name
            }
        } else {
            gui.screenHandler.slots.firstOrNull {
                it.stack.item == item &&
                        it.stack.components.get(DataComponentTypes.CUSTOM_NAME)?.string == name
            }
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

    suspend fun <V> onOpen(name: String, action: suspend (GenericContainerScreen) -> V?, timeout: suspend () -> Unit = {}): V? {
        var attempts = 0
        while (true) {
            if (attempts++ >= 50) {
                timeout()
                return null
            }
            val gui = MC.currentScreen as? GenericContainerScreen
            if (gui != null && gui.title.string.contains(name)) {
                return action(gui)
            }

            delay(50)
        }
    }

    fun clickMenuSlotWithDelay(delayMs: Long, vararg slots: MenuSlot) {
        if (delayMs == 0L) {
            clickMenuSlot(*slots)
        } else {
            CompletableFuture.runAsync({
                clickMenuSlot(*slots)
            }, CompletableFuture.delayedExecutor(delayMs, TimeUnit.MILLISECONDS))
        }
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