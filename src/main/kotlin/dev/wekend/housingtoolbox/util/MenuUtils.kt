package dev.wekend.housingtoolbox.util

import dev.wekend.housingtoolbox.HousingToolbox.MC
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeoutOrNull
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.component.DataComponentTypes
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.screen.slot.Slot
import net.minecraft.screen.slot.SlotActionType
import kotlin.reflect.KClass

object MenuUtils {

    data class Target(val menuSlot: MenuSlot, val button: Int = 0)
    data class MenuSlot(val item: Item?, val label: String?, val slot: Int? = null)

    inline fun withContainer(block: (GenericContainerScreen) -> Boolean): Boolean {
        val gui = MC.currentScreen as? GenericContainerScreen ?: return false
        return block(gui)
    }

    fun findSlot(gui: GenericContainerScreen, menuSlot: MenuSlot) =
        findSlot(gui, menuSlot.item, menuSlot.label, menuSlot.slot)

    fun findSlot(gui: GenericContainerScreen, item: Item?, name: String?, slot: Int?): Slot? {
        if (slot != null) {
            val s = gui.screenHandler.getSlot(slot)
            return when {
                name == null && item == null -> s
                name == null && s.stack.item == item -> s
                item == null && s.stack.components.get(DataComponentTypes.CUSTOM_NAME)?.string == name -> s
                s.stack.item == item && s.stack.components.get(DataComponentTypes.CUSTOM_NAME)?.string == name -> s
                else -> null
            }
        }

        return gui.screenHandler.slots.firstOrNull { slot ->
            when {
                name == null && item == null -> true
                name == null -> slot.stack.item == item
                item == null -> slot.stack.components.get(DataComponentTypes.CUSTOM_NAME)?.string == name
                else -> slot.stack.item == item &&
                        slot.stack.components.get(DataComponentTypes.CUSTOM_NAME)?.string == name
            }
        }
    }

    fun click(gui: HandledScreen<*>, slot: Int, button: Int = 0) {
        MC.interactionManager?.clickSlot(
            gui.screenHandler.syncId,
            slot,
            button,
            SlotActionType.PICKUP,
            MC.player
        )
    }

    var waitingOn: String? = null
    var currentScreen: String? = null
    var attempts: Int = 0

    suspend fun onOpen(name: String?, vararg clazz: KClass<out Screen> = arrayOf(GenericContainerScreen::class)): Screen {
        attempts = 0
        waitingOn = name ?: "null"
        while (true) {
            if (attempts++ >= 20) error("Failed to open $name")
            delay(50)
            val screen = MC.currentScreen ?: continue
            currentScreen = screen.title.string
            if (clazz.contains(screen::class) && (name == null || currentScreen?.contains(name) == true)) {
                delay(50)
                return screen
            }
        }
    }

    suspend fun delayClick(gui: HandledScreen<*>, slot: Int, button: Int, delayMs: Long) {
        delay(delayMs)
        click(gui, slot, button)
    }

    suspend fun clickMenuSlotWithDelay(delayMs: Long, vararg slots: MenuSlot) {
        if (delayMs == 0L) {
            clickMenuSlot(*slots)
        } else {
            delay(delayMs)
            clickMenuSlot(*slots)
        }
    }

    fun clickMenuSlot(vararg slots: MenuSlot): Boolean =
        clickMenuTargets(*slots.map { Target(it) }.toTypedArray())

    fun clickMenuTargets(vararg attempts: Target): Boolean =
        withContainer { gui ->
            val match = attempts.firstNotNullOfOrNull {
                val slot = findSlot(gui, it.menuSlot)
                if (slot != null) it to slot else null
            } ?: return@withContainer false
            click(gui, match.second.id, match.first.button)
            true
        }

    suspend fun clickMenuTargetPaginated(vararg attempts: Target): Boolean {
        return withContainer { gui ->
            val match = attempts.firstNotNullOfOrNull {
                val slot = findSlot(gui, it.menuSlot)
                if (slot != null) it to slot else null
            }

            if (match == null) {
                val nextPageSlot = findSlot(gui, GlobalMenuItems.NEXT_PAGE)
                if (nextPageSlot != null) {
                    clickMenuSlot(GlobalMenuItems.NEXT_PAGE)
                    delay(100)
                    if (clickMenuTargetPaginated(*attempts)) {
                        return true
                    }
                }
                false
            } else {
                click(gui, match.second.id, match.first.button)
                true
            }
        }
    }

    fun clickPlayerSlot(slot: Int, button: Int = 0) =
        withContainer { gui ->
            val playerSlot = slot + gui.screenHandler.slots.size - 45
            click(gui, playerSlot, button)
            true
        }

    suspend fun clickPlayerSlotWithDelay(delayMs: Long, slot: Int, button: Int = 0) {
        if (delayMs == 0L) {
            clickPlayerSlot(slot, button)
        } else {
            delay(delayMs)
            clickPlayerSlot(slot, button)
        }
    }

    object GlobalMenuItems {
        val NEXT_PAGE = MenuSlot(Items.ARROW, "Left-click for next page!")
    }
}