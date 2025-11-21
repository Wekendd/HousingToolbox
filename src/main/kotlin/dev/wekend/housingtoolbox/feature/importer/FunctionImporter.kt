package dev.wekend.housingtoolbox.feature.importer

import dev.wekend.housingtoolbox.HousingToolbox.MC
import dev.wekend.housingtoolbox.api.Function
import dev.wekend.housingtoolbox.feature.data.Action
import dev.wekend.housingtoolbox.feature.data.ItemStack
import dev.wekend.housingtoolbox.util.CommandUtils
import dev.wekend.housingtoolbox.util.ItemUtils
import dev.wekend.housingtoolbox.util.ItemUtils.loreLine
import dev.wekend.housingtoolbox.util.MenuUtils
import dev.wekend.housingtoolbox.util.MenuUtils.MenuSlot
import dev.wekend.housingtoolbox.util.MenuUtils.Target
import dev.wekend.housingtoolbox.util.TextUtils
import kotlinx.coroutines.delay
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen
import net.minecraft.item.Item
import net.minecraft.item.Items

internal class FunctionImporter(override var name: String) : Function {
    private fun isFunctionEditMenuOpen(): Boolean {
        val container = MC.currentScreen as? GenericContainerScreen ?: return false
        return container.title.string.contains("Edit: $name")
    }

    private fun isActionsMenuOpen(): Boolean {
        val container = MC.currentScreen as? GenericContainerScreen ?: return false
        return container.title.string.contains("Actions: $name")
    }

    private suspend fun openFunctionEditMenu(): Boolean {
        if (!isFunctionEditMenuOpen()) {
            CommandUtils.runCommand("functions")
            MenuUtils.onOpen("Functions")

            if (!MenuUtils.clickMenuTargetPaginated(Target(MenuSlot(null, name), 1))) return false

            MenuUtils.onOpen("Edit: $name")
            delay(50)
        }
        return true
    }

    private suspend fun openActionsEditMenu(): Boolean {
        if (!isActionsMenuOpen()) {
            CommandUtils.runCommand("function edit $name")
            MenuUtils.onOpen("Actions: $name")
            delay(50)
        }
        return true
    }

    override suspend fun setName(newName: String) {
        openFunctionEditMenu()

        MenuUtils.clickMenuSlot(MenuItems.RENAME_FUNCTION)
        TextUtils.input(newName, 100L)

        name = newName
    }

    override suspend fun createIfNotExists() {
        if (!openFunctionEditMenu()) {
            CommandUtils.runCommand("function create $name")
            MenuUtils.onOpen("Actions: $name")
            MenuUtils.clickMenuSlot(MenuItems.BACK)
            delay(100)
        }
    }

    override suspend fun getDescription(): String {
        openFunctionEditMenu()

        val gui = MC.currentScreen as? GenericContainerScreen ?: return "" // FIXME
        val slot = MenuUtils.findSlot(gui, MenuItems.SET_DESCRIPTION) ?: return "" // FIXME
        return slot.stack.loreLine(2, true, LoreFilters.RENAME_LORE_FILTER)
    }

    override suspend fun setDescription(newDescription: String) {
        openFunctionEditMenu()

        val gui = MC.currentScreen as? GenericContainerScreen ?: return // FIXME
        val slot = MenuUtils.findSlot(gui, MenuItems.SET_DESCRIPTION) ?: return // FIXME
        val value = slot.stack.loreLine(2, true, LoreFilters.RENAME_LORE_FILTER)
        if (value == newDescription) return

        MenuUtils.clickMenuSlot(MenuItems.SET_DESCRIPTION)
        TextUtils.input(newDescription, 100L)
    }

    override suspend fun getIcon(): Item {
        openFunctionEditMenu()

        val gui = MC.currentScreen as? GenericContainerScreen ?: return Items.AIR // FIXME

        val slot = MenuUtils.findSlot(gui, MenuItems.EDIT_ICON) ?: return Items.AIR // FIXME
        return slot.stack.item
    }

    override suspend fun setIcon(newIcon: ItemStack) {
        openFunctionEditMenu()

        MenuUtils.clickMenuSlot(MenuItems.EDIT_ICON)
        MenuUtils.onOpen("Select an Item")

        val itemstack = ItemUtils.createFromNBT(newIcon.nbt ?: return)
        ItemUtils.placeInventory(itemstack, 26)
        MenuUtils.clickPlayerSlot(26)
        delay(50)
    }

    override suspend fun getAutomaticExecution(): Int {
        openFunctionEditMenu()

        val gui = MC.currentScreen as? GenericContainerScreen ?: return 0 // FIXME
        val slot = MenuUtils.findSlot(gui, MenuItems.AUTOMATIC_EXECUTION) ?: return 0 // FIXME
        val loreLine = slot.stack.loreLine(5, false)
        val part = loreLine.split(" ")[1]

        return if (part == "Disabled") 0 else part.toIntOrNull() ?: 0 // FIXME
    }

    override suspend fun setAutomaticExecution(newAutomaticExecution: Int) {
        openFunctionEditMenu()

        val gui = MC.currentScreen as? GenericContainerScreen ?: return // FIXME
        val slot = MenuUtils.findSlot(gui, MenuItems.AUTOMATIC_EXECUTION) ?: return // FIXME
        val loreLine = slot.stack.loreLine(5, false)
        val part = loreLine.split(" ")[1]
        val value = if (part == "Disabled") 0 else part.toIntOrNull() ?: 0 // FIXME
        if (value == newAutomaticExecution) return

        MenuUtils.clickMenuSlot(MenuItems.AUTOMATIC_EXECUTION)
        TextUtils.input(newAutomaticExecution.toString(), 100L)
    }

    override suspend fun getActions(): List<Action> {
        TODO("Not yet implemented")
    }

    override suspend fun addActions(newActions: List<Action>) {
        if (!isActionsMenuOpen()) CommandUtils.runCommand("function edit $name")
        ActionInteraction("Actions: $name").addActions(newActions)
    }

    override suspend fun delete() {
        CommandUtils.runCommand("function delete $name")
    }

    object MenuItems {
        val RENAME_FUNCTION = MenuSlot(Items.ANVIL, "Rename Function")
        val SET_DESCRIPTION = MenuSlot(Items.BOOK, "Edit Description")
        val EDIT_ICON = MenuSlot(null, "Edit Icon")
        val AUTOMATIC_EXECUTION = MenuSlot(Items.COMPARATOR, "Automatic Execution")
        val BACK = MenuSlot(Items.ARROW, "Go Back")
    }

    object LoreFilters {
        val RENAME_LORE_FILTER: (String) -> Boolean = { loreLine ->
            !loreLine.contains("Click to rename!")
        }
    }
}