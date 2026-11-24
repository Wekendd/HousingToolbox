package dev.wekend.housingtoolbox.keybind

import com.mojang.blaze3d.platform.InputConstants
import de.siphalor.amecs.key_modifiers.api.AmecsKeyModifierCombination
import de.siphalor.amecs.key_modifiers.api.AmecsKeyModifiers
import dev.wekend.housingtoolbox.keybind.KeyBindings.KeySpec
import dev.wekend.housingtoolbox.util.MenuUtils
import dev.wekend.housingtoolbox.util.MenuUtils.clickMenuTargets
import net.minecraft.world.item.Items

object KeyNav {

    fun init() {
        KeyBindings.registerAll(
            listOf(
                KeySpec(
                    id = "search",
                    key = InputConstants.UNKNOWN.value,
                    modifiers = AmecsKeyModifierCombination(AmecsKeyModifiers.CONTROL),
                    onPress = { MenuUtils.clickMenuSlot(MenuItems.SEARCH, MenuItems.SEARCH2, MenuItems.FILTER) }
                ),
                KeySpec(
                    id = "copy",
                    key = InputConstants.KEY_C,
                    modifiers = AmecsKeyModifierCombination(AmecsKeyModifiers.CONTROL),
                    onPress = {
                        clickMenuTargets(
                            MenuUtils.Target(MenuItems.COPY, 0),
                            MenuUtils.Target(MenuItems.PASTE, 1)
                        )
                    }
                ),
                KeySpec(
                    id = "paste",
                    key = InputConstants.KEY_V,
                    modifiers = AmecsKeyModifierCombination(AmecsKeyModifiers.CONTROL),
                    onPress = {
                        clickMenuTargets(
                            MenuUtils.Target(MenuItems.PASTE, 0),
                            MenuUtils.Target(MenuItems.COPY, 1)
                        )
                    }
                ),
                KeySpec(
                    id = "undo",
                    key = InputConstants.KEY_Z,
                    modifiers = AmecsKeyModifierCombination(AmecsKeyModifiers.CONTROL),
                    onPress = { MenuUtils.clickMenuSlot(MenuItems.UNDO) }
                ),
                KeySpec(
                    id = "back",
                    key = InputConstants.UNKNOWN.value,
                    onPress = { MenuUtils.clickMenuSlot(MenuItems.BACK, MenuItems.MAIN_MENU, MenuItems.CLOSE) }
                ),
                KeySpec(
                    id = "nextpage",
                    key = InputConstants.UNKNOWN.value,
                    onPress = { MenuUtils.clickMenuSlot(MenuItems.NEXT_PAGE) }
                ),
                KeySpec(
                    id = "prevpage",
                    key = InputConstants.UNKNOWN.value,
                    onPress = { MenuUtils.clickMenuSlot(MenuItems.PREV_PAGE) }
                )
            )
        )
    }

    object MenuItems {
        val MAIN_MENU = MenuUtils.MenuSlot(Items.NETHER_STAR, "Main Menu")
        val SEARCH = MenuUtils.MenuSlot(Items.COMPASS, "Search")
        val SEARCH2 = MenuUtils.MenuSlot(Items.OAK_SIGN, "Search")
        val FILTER = MenuUtils.MenuSlot(Items.OAK_SIGN, "Filter")
        val COPY = MenuUtils.MenuSlot(Items.WRITABLE_BOOK, "Copy Actions")
        val UNDO = MenuUtils.MenuSlot(Items.CAULDRON, "Restore Last Removed Action")
        val PASTE = MenuUtils.MenuSlot(Items.BOOK, "Paste Actions")
        val BACK = MenuUtils.MenuSlot(Items.ARROW, "Go Back")
        val CLOSE = MenuUtils.MenuSlot(Items.BARRIER, "Close")
        val NEXT_PAGE = MenuUtils.MenuSlot(Items.ARROW, "Left-click for next page!")
        val PREV_PAGE = MenuUtils.MenuSlot(Items.ARROW, "Left-click for previous page!")
    }

}