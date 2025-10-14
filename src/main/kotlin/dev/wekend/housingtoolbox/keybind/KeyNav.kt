package dev.wekend.housingtoolbox.keybind

import de.siphalor.amecs.api.KeyModifiers
import dev.wekend.housingtoolbox.keybind.KeyBindings.KeySpec
import dev.wekend.housingtoolbox.util.MenuUtils
import net.minecraft.item.Item
import net.minecraft.item.Items
import org.lwjgl.glfw.GLFW

object KeyNav {

    fun init() {
        KeyBindings.registerAll(
            listOf(
                KeySpec(
                    id = "search",
                    key = GLFW.GLFW_KEY_F,
                    modifiers = KeyModifiers(false, true, false), // Ctrl+F
                    onPress = { MenuUtils.clickMenuSlot(MenuSlot.SEARCH, MenuSlot.FILTER) }
                ),
                KeySpec(
                    id = "back",
                    key = GLFW.GLFW_KEY_UNKNOWN,
                    onPress = { MenuUtils.clickMenuSlot(MenuSlot.BACK, MenuSlot.MAIN_MENU, MenuSlot.CLOSE) }
                ),
                KeySpec(
                    id = "nextpage",
                    key = GLFW.GLFW_KEY_UNKNOWN,
                    onPress = { MenuUtils.clickMenuSlot(MenuSlot.NEXT_PAGE) }
                ),
                KeySpec(
                    id = "prevpage",
                    key = GLFW.GLFW_KEY_UNKNOWN,
                    onPress = { MenuUtils.clickMenuSlot(MenuSlot.PREV_PAGE) }
                )
            )
        )
    }

    enum class MenuSlot(val item: Item, val label: String) {
        MAIN_MENU(Items.NETHER_STAR, "Main Menu"),
        SEARCH(Items.COMPASS, "Search"),
        FILTER(Items.OAK_SIGN, "Filter"),
        BACK(Items.ARROW, "Go Back"),
        CLOSE(Items.BARRIER, "Close"),
        NEXT_PAGE(Items.ARROW, "Left-click for next page!"),
        PREV_PAGE(Items.ARROW, "Left-click for previous page!")
    }

}