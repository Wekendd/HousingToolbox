package dev.wekend.housingtoolbox.feature.importer

import dev.wekend.housingtoolbox.HousingToolbox.MC
import dev.wekend.housingtoolbox.api.Command
import dev.wekend.housingtoolbox.feature.data.Action
import dev.wekend.housingtoolbox.util.CommandUtils
import dev.wekend.housingtoolbox.util.ItemUtils.loreLine
import dev.wekend.housingtoolbox.util.MenuUtils
import dev.wekend.housingtoolbox.util.MenuUtils.MenuSlot
import dev.wekend.housingtoolbox.util.TextUtils
import kotlinx.coroutines.delay
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen
import net.minecraft.item.Items

internal class CommandImporter(override var name: String) : Command {
    private fun isCommandEditMenuOpen(): Boolean {
        val container = MC.currentScreen as? GenericContainerScreen ?: return false
        return container.title.string.contains("Edit /$name") // absence of colon is intentional; hypixel weird
    }

    private fun isActionsMenuOpen(): Boolean {
        val container = MC.currentScreen as? GenericContainerScreen ?: return false
        return container.title.string.contains("Actions: /$name")
    }

    private suspend fun openCommandEditMenu(): Boolean {
        if (!isCommandEditMenuOpen()) {
            CommandUtils.runCommand("command edit $name")
            MenuUtils.onOpen("Edit /$name")
            delay(50)
        }
        return true
    }

    private suspend fun openActionsEditMenu(): Boolean {
        if (!isActionsMenuOpen()) {
            CommandUtils.runCommand("command actions $name")
            MenuUtils.onOpen("Actions: /$name")
            delay(50)
        }
        return true
    }

    override suspend fun setName(newName: String) {
        openCommandEditMenu()

        MenuUtils.clickMenuSlot(MenuItems.RENAME_COMMAND)
        TextUtils.input(newName, 100L)

        name = newName
    }

    override suspend fun createIfNotExists() {
        if (!openCommandEditMenu()) {
            CommandUtils.runCommand("command create $name")
            MenuUtils.onOpen("Actions: /$name")
            MenuUtils.clickMenuSlot(MenuItems.BACK)
            delay(100)
        }
    }

    override suspend fun getCommandMode(): Command.CommandMode {
        openCommandEditMenu()

        val gui = MC.currentScreen as? GenericContainerScreen ?: return Command.CommandMode.TARGETED // FIXME
        val slot = MenuUtils.findSlot(gui, MenuItems.TOGGLE_COMMAND_MODE) ?: return Command.CommandMode.TARGETED // FIXME
        val loreLine = slot.stack.loreLine(1, false)
        val part = loreLine.split(" ")[1]
        return if (part == "Self") Command.CommandMode.SELF else Command.CommandMode.TARGETED
    }

    override suspend fun setCommandMode(newCommandMode: Command.CommandMode) {
        openCommandEditMenu()

        val gui = MC.currentScreen as? GenericContainerScreen ?: return // FIXME: error
        val slot = MenuUtils.findSlot(gui, MenuItems.TOGGLE_COMMAND_MODE) ?: return // FIXME: error
        val loreLine = slot.stack.loreLine(1, false)
        val part = loreLine.split(" ")[1]
        val value = if (part == "Self") Command.CommandMode.SELF else Command.CommandMode.TARGETED
        if (newCommandMode != value) MenuUtils.clickMenuSlot(MenuItems.TOGGLE_COMMAND_MODE)
    }

    override suspend fun getRequiredGroupPriority(): Int {
        openCommandEditMenu()

        val gui = MC.currentScreen as? GenericContainerScreen ?: return 0 // FIXME
        val slot = MenuUtils.findSlot(gui, MenuItems.REQUIRED_GROUP_PRIORITY) ?: return 0 // FIXME
        val loreLine = slot.stack.loreLine(4, false)
        val part = loreLine.split(" ")[1]
        return part.toIntOrNull() ?: return 0 // FIXME
    }

    override suspend fun setRequiredGroupPriority(newPriority: Int) {
        openCommandEditMenu()

        val gui = MC.currentScreen as? GenericContainerScreen ?: return // FIXME
        val slot = MenuUtils.findSlot(gui, MenuItems.REQUIRED_GROUP_PRIORITY) ?: return // FIXME
        val loreLine = slot.stack.loreLine(4, false)
        val part = loreLine.split(" ")[1]
        val value = part.toIntOrNull() ?: 0 // FIXME
        if (value == newPriority) return

        MenuUtils.clickMenuSlot(MenuItems.REQUIRED_GROUP_PRIORITY)
        TextUtils.input(newPriority.toString(), 100L)
    }

    override suspend fun getListed(): Boolean {
        openCommandEditMenu()

        val gui = MC.currentScreen as? GenericContainerScreen ?: return false // FIXME
        val slot = MenuUtils.findSlot(gui, MenuItems.REQUIRED_GROUP_PRIORITY) ?: return false // FIXME
        return slot.stack.item == Items.LIME_DYE
    }

    override suspend fun setListed(newListed: Boolean) {
        openCommandEditMenu()

        val gui = MC.currentScreen as? GenericContainerScreen ?: return // FIXME
        val slot = MenuUtils.findSlot(gui, MenuItems.REQUIRED_GROUP_PRIORITY) ?: return // FIXME
        val value = slot.stack.item == Items.LIME_DYE
        if (value == newListed) return

        MenuUtils.clickMenuSlot(MenuItems.LISTED)
    }

    override suspend fun getActions(): List<Action> {
        TODO("Not yet implemented")
    }

    override suspend fun setActions(newActions: List<Action>, optimized: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        openCommandEditMenu()

        MenuUtils.clickMenuSlot(MenuItems.DELETE_COMMAND)
        MenuUtils.onOpen("Are you sure?")
        MenuUtils.clickMenuSlot(MenuItems.CONFIRM)
    }

    object MenuItems {
        val RENAME_COMMAND = MenuSlot(Items.ANVIL, "Rename Command")
        val TOGGLE_COMMAND_MODE = MenuSlot(null, "Toggle Command Mode")
        val REQUIRED_GROUP_PRIORITY = MenuSlot(Items.FILLED_MAP, "Required Group Priority")
        val LISTED = MenuSlot(null, "Listed")
        val DELETE_COMMAND = MenuSlot(Items.TNT, "Delete Command")
        val BACK = MenuSlot(Items.ARROW, "Go Back")
        val CONFIRM = MenuSlot(Items.GREEN_TERRACOTTA, "Confirm")
    }

    object LoreFilters {
        val RENAME_LORE_FILTER: (String) -> Boolean = { loreLine ->
            !loreLine.contains("Click to rename!")
        }
    }
}