package dev.wekend.housingtoolbox.keybind

import com.github.shynixn.mccoroutine.fabric.MCCoroutine
import com.github.shynixn.mccoroutine.fabric.launch
import de.siphalor.amecs.api.KeyModifiers
import dev.wekend.housingtoolbox.HousingToolbox
import dev.wekend.housingtoolbox.feature.importer.FunctionImporter
import dev.wekend.housingtoolbox.keybind.KeyBindings.KeySpec
import dev.wekend.housingtoolbox.util.MenuUtils
import dev.wekend.housingtoolbox.util.MenuUtils.clickMenuTargets
import net.minecraft.client.MinecraftClient
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtOps
import org.lwjgl.glfw.GLFW
import kotlin.jvm.optionals.getOrNull

object KeyNav {

    fun init() {
        KeyBindings.registerAll(
            listOf(
                KeySpec(
                    id = "search",
                    key = GLFW.GLFW_KEY_F,
                    modifiers = KeyModifiers(false, true, false),
                    onPress = { MenuUtils.clickMenuSlot(MenuItems.SEARCH, MenuItems.FILTER) }
                ),
                KeySpec(
                    id = "copy",
                    key = GLFW.GLFW_KEY_C,
                    modifiers = KeyModifiers(false, true, false),
                    onPress = {
                        clickMenuTargets(
                            MenuUtils.Target(MenuItems.COPY, 0),
                            MenuUtils.Target(MenuItems.PASTE, 1)
                        )
                    }
                ),
                KeySpec(
                    id = "copyitemnbt",
                    key = GLFW.GLFW_KEY_C,
                    modifiers = KeyModifiers(false, true, true),
                    onPress = {
                        val stack = MinecraftClient.getInstance().player?.inventory?.selectedStack
                        val result = ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, stack).result().getOrNull()

                        if (result != null) {
                            val str = result.toString()
                            MinecraftClient.getInstance().keyboard.setClipboard(str)
                            true
                        } else {
                            false
                        }
                    }
                ),
                KeySpec(
                    id = "test",
                    key = GLFW.GLFW_KEY_Z,
                    modifiers = KeyModifiers(false, false, false),
                    onPress = {
                        HousingToolbox.launch {
                            println("test keybind pressed")
                            val importer = FunctionImporter("test")

                            println("Function stack: ${importer.getIcon()}")
                        }
                        true
                    }
                ),
                KeySpec(
                    id = "paste",
                    key = GLFW.GLFW_KEY_V,
                    modifiers = KeyModifiers(false, true, false),
                    onPress = {
                        clickMenuTargets(
                            MenuUtils.Target(MenuItems.PASTE, 0),
                            MenuUtils.Target(MenuItems.COPY, 1)
                        )
                    }
                ),
                KeySpec(
                    id = "undo",
                    key = GLFW.GLFW_KEY_Z,
                    modifiers = KeyModifiers(false, true, false),
                    onPress = { MenuUtils.clickMenuSlot(MenuItems.UNDO) }
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
        val UNDO = MenuUtils.MenuSlot(Items.CAULDRON, "Restore Last Removed Action")
        val PASTE = MenuUtils.MenuSlot(Items.BOOK, "Paste Actions")
        val BACK = MenuUtils.MenuSlot(Items.ARROW, "Go Back")
        val CLOSE = MenuUtils.MenuSlot(Items.BARRIER, "Close")
        val NEXT_PAGE = MenuUtils.MenuSlot(Items.ARROW, "Left-click for next page!")
        val PREV_PAGE = MenuUtils.MenuSlot(Items.ARROW, "Left-click for previous page!")
    }

}