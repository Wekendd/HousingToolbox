package dev.wekend.housingtoolbox.keybind

import de.siphalor.amecs.api.KeyModifiers
import dev.wekend.housingtoolbox.keybind.KeyBindings.KeySpec
import dev.wekend.housingtoolbox.util.MenuUtils
import dev.wekend.housingtoolbox.util.MenuUtils.clickMenuTargets
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.screen.slot.SlotActionType
import org.lwjgl.glfw.GLFW

object KeyNav {

    fun init() {
        KeyBindings.registerAll(
            listOf(
                KeySpec(
                    id = "search",
                    key = GLFW.GLFW_KEY_F,
                    modifiers = KeyModifiers(false, true, false), // Ctrl+F
                    onPress = { MenuUtils.clickMenuSlot(MenuItems.SEARCH, MenuItems.FILTER) }
                ),
                KeySpec(
                    id = "copy",
                    key = GLFW.GLFW_KEY_C,
                    modifiers = KeyModifiers(false, true, false), // Ctrl+F
                    onPress = {
                        clickMenuTargets(
                            MenuUtils.Target(MenuItems.COPY, 0),
                            MenuUtils.Target(MenuItems.PASTE, 1)
                        )
                    }
                ),
                KeySpec(
                    id = "paste",
                    key = GLFW.GLFW_KEY_V,
                    modifiers = KeyModifiers(false, true, false), // Ctrl+F
                    onPress = {
                        clickMenuTargets(
                            MenuUtils.Target(MenuItems.PASTE, 0),
                            MenuUtils.Target(MenuItems.COPY, 1)
                        )
                    }
                ),
                KeySpec(
                    id = "back",
                    key = GLFW.GLFW_KEY_UNKNOWN,
                    onPress = { MenuUtils.clickMenuSlot(MenuItems.BACK, MenuItems.MAIN_MENU, MenuItems.CLOSE) }
                ),
                KeySpec(
                    id = "nextpage",
                    key = GLFW.GLFW_KEY_UNKNOWN,
                    onPress = { MenuUtils.clickMenuSlot(MenuItems.NEXT_PAGE) }
                ),
                KeySpec(
                    id = "prevpage",
                    key = GLFW.GLFW_KEY_UNKNOWN,
                    onPress = { MenuUtils.clickMenuSlot(MenuItems.PREV_PAGE) }
                )
            )
        )
    }

    object MenuItems {
        val MAIN_MENU = MenuUtils.MenuSlot(Items.NETHER_STAR, "Main Menu")
        val SEARCH = MenuUtils.MenuSlot(Items.COMPASS, "Search")
        val FILTER = MenuUtils.MenuSlot(Items.OAK_SIGN, "Filter")
        val COPY = MenuUtils.MenuSlot(Items.WRITABLE_BOOK, "Copy Actions")
        val PASTE = MenuUtils.MenuSlot(Items.BOOK, "Paste Actions")
        val BACK = MenuUtils.MenuSlot(Items.ARROW, "Go Back")
        val CLOSE = MenuUtils.MenuSlot(Items.BARRIER, "Close")
        val NEXT_PAGE = MenuUtils.MenuSlot(Items.ARROW, "Left-click for next page!")
        val PREV_PAGE = MenuUtils.MenuSlot(Items.ARROW, "Left-click for previous page!")
    }

}