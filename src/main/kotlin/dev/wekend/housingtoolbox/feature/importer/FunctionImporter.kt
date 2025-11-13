package dev.wekend.housingtoolbox.feature.importer

import dev.wekend.housingtoolbox.HousingToolbox.MC
import dev.wekend.housingtoolbox.api.Function
import dev.wekend.housingtoolbox.feature.data.Action
import dev.wekend.housingtoolbox.keybind.KeyNav
import dev.wekend.housingtoolbox.util.CommandUtils
import dev.wekend.housingtoolbox.util.ItemUtils.loreLine
import dev.wekend.housingtoolbox.util.MenuUtils
import dev.wekend.housingtoolbox.util.TextUtils
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen
import net.minecraft.component.DataComponentTypes
import net.minecraft.item.Item
import net.minecraft.item.Items
import java.awt.Menu

internal class FunctionImporter(override var name: String) : Function {
    private fun isEditMenuOpen(): Boolean {
        val container = MC.currentScreen as? GenericContainerScreen ?: return false
        return container.title.string.contains("Edit: $name")
    }

    private fun isActionsMenuOpen(): Boolean {
        val container = MC.currentScreen as? GenericContainerScreen ?: return false
        return container.title.string.contains("Actions: $name")
    }

    private suspend fun openFunctionEditMenu() {
        if (!isEditMenuOpen()) {
            CommandUtils.runCommand("functions")

            MenuUtils.onOpen("Functions", action = {
                MenuUtils.clickMenuTargets(MenuUtils.Target(MenuUtils.MenuSlot(null, name), 1))
            })
        }
    }

    override suspend fun setName(newName: String) {
        openFunctionEditMenu()

        MenuUtils.onOpen("Edit: $name", action = {
            MenuUtils.clickMenuSlot(MenuItems.RENAME_FUNCTION)
            TextUtils.input(newName, 100L)
            name = newName
        })
    }

    override suspend fun getDescription(): String {
        openFunctionEditMenu()

        return MenuUtils.onOpen("Edit: $name", action = {
            val slot = MenuUtils.findSlot(it, MenuItems.SET_DESCRIPTION) ?: return@onOpen ""
            slot.stack.loreLine(2, LoreFilters.RENAME_LORE_FILTER)
        }) ?: ""
    }

    override suspend fun setDescription(newDescription: String) {
        openFunctionEditMenu()

        MenuUtils.onOpen("Edit: $name", action = {
            MenuUtils.clickMenuSlot(MenuItems.SET_DESCRIPTION)
            TextUtils.input(newDescription, 100L)
        })

    }

    override suspend fun getIcon(): Item {
        openFunctionEditMenu()

        return MenuUtils.onOpen("Edit: $name", action = {
            val slot = MenuUtils.findSlot(it, MenuItems.EDIT_ICON) ?: return@onOpen Items.AIR
            return@onOpen slot.stack.item
        }) ?: Items.AIR
    }

    override suspend fun setIcon(newIcon: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun getAutomaticExecution(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun setAutomaticExecution(newAutomaticExecution: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getActions(): List<Action> {
        TODO("Not yet implemented")
    }

    override suspend fun setActions(
        newActions: List<Action>,
        optimized: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        TODO("Not yet implemented")
    }

    object MenuItems {
        val RENAME_FUNCTION = MenuUtils.MenuSlot(Items.ANVIL, "Rename Function")
        val SET_DESCRIPTION = MenuUtils.MenuSlot(Items.BOOK, "Edit Description")
        val EDIT_ICON = MenuUtils.MenuSlot(Items.MAP, "Edit Icon")
        val DELETE_FUNCTION = MenuUtils.MenuSlot(Items.TNT, "Delete Function")
        val AUTOMATIC_EXECUTION = MenuUtils.MenuSlot(Items.COMPARATOR, "Automatic Execution")
        val BACK = MenuUtils.MenuSlot(Items.ARROW, "Go Back")
    }

    object LoreFilters {
        val RENAME_LORE_FILTER: (String) -> Boolean = { loreLine ->
            !loreLine.contains("Click to rename!")
        }
    }
}