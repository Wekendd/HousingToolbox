package dev.wekend.housingtoolbox.feature.importer

import dev.wekend.housingtoolbox.HousingToolbox.MC
import dev.wekend.housingtoolbox.feature.data.Action
import dev.wekend.housingtoolbox.feature.data.DisplayName
import dev.wekend.housingtoolbox.feature.data.Keyed
import dev.wekend.housingtoolbox.feature.data.StatValue
import dev.wekend.housingtoolbox.util.ItemUtils.loreLine
import dev.wekend.housingtoolbox.util.MenuUtils
import dev.wekend.housingtoolbox.util.MenuUtils.MenuSlot
import dev.wekend.housingtoolbox.util.MenuUtils.Target
import dev.wekend.housingtoolbox.util.TextUtils
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen
import net.minecraft.item.Items
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.starProjectedType

class ActionImporter(val actions: List<Action>) {
    companion object {
        private val slots = mapOf(
            0 to 10,
            1 to 11,
            2 to 12,
            3 to 13,
            4 to 14,
            5 to 15,
            6 to 16,
            7 to 19,
            8 to 20,
        )
    }

    /*
    Anyone who reads this is in for a treat lol
     */
    suspend fun import(title: String) {
        for (action in actions) {
            MenuUtils.onOpen(title)
            MenuUtils.clickMenuSlot(MenuItems.ADD_ACTION)
            MenuUtils.onOpen("Add Action")
            val gui = MC.currentScreen as? GenericContainerScreen ?: return
            val parameters = action::class.primaryConstructor!!.parameters
            val properties = action.javaClass.kotlin.memberProperties
            val displayName = (action::class.annotations.find { it is DisplayName } as DisplayName).value

            MenuUtils.clickMenuTargetPaginated(Target(MenuSlot(null, displayName)))
            if (properties.isEmpty()) continue
            parameters.forEachIndexed { index, param ->
                val slotIndex = slots[index]!!

                val property = properties.find { it.name == param.name } ?: return@forEachIndexed
                val value = property.get(action)
                val slot = gui.screenHandler.getSlot(slotIndex)
                MenuUtils.onOpen("Action Settings")
                if (property.returnType.isSubtypeOf(Keyed::class.starProjectedType)) {
                    val keyed = value as Keyed
                    MenuUtils.clickMenuSlot(MenuSlot(null, null, slotIndex))
                    MenuUtils.onOpen("Select Option")
                    MenuUtils.clickMenuTargetPaginated(Target(MenuSlot(null, keyed.key)))

                    return@forEachIndexed
                }
                when (property.returnType.classifier) {
                    String::class, Int::class, Double::class -> {
                        MenuUtils.clickMenuSlot(MenuSlot(null, null, slotIndex))
                        TextUtils.input(value.toString(), 100L)
                    }

                    StatValue.Dbl::class, StatValue.I32::class, StatValue.Str::class, StatValue.Lng::class -> {
                        MenuUtils.clickMenuSlot(MenuSlot(null, null, slotIndex))
                        TextUtils.input(value.toString(), 100L)
                    }

                    Boolean::class -> {
                        val line = slot.stack.loreLine(false, filter = { str -> str == "Disabled" || str == "Enabled" }) ?: return@forEachIndexed
                        val currentValue = line == "Enabled"
                        val boolValue = value as Boolean
                        if (currentValue != boolValue) {
                            MenuUtils.clickMenuSlot(MenuSlot(null, null, slotIndex))
                        }
                    }


                }
            }
            MenuUtils.onOpen("Action Settings")
            MenuUtils.clickMenuSlot(MenuItems.BACK)
        }
    }

    object MenuItems {
        val ADD_ACTION = MenuSlot(Items.PAPER, "Add Action")
        val BACK = MenuSlot(Items.ARROW, "Go Back")
    }
}