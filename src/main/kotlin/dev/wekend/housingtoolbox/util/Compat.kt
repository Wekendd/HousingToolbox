package dev.wekend.housingtoolbox.util

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.multiplayer.MultiPlayerGameMode
import net.minecraft.world.entity.player.Player

// ==========================================
// ClickType / ContainerInput Compat (26.1+)
// ==========================================
//? if >=26.1 {
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.world.inventory.ContainerInput
typealias ClickAction = ContainerInput
//?} else {
/*import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.world.inventory.ClickType
typealias ClickAction = ClickType
*///?}

// ==========================================
// Key constant compat
// ==========================================
fun inputConstantKeyboardType(): InputConstants.Type {
    //? if >=26.3 {
    return InputConstants.Type.KEYBOARD
    //?} else {
    /*return InputConstants.Type.KEYSYM
    *///?}
}

// ==========================================
// Inventory Click Compat
// ==========================================
/**
 * Executes a PICKUP click abstracting the method and enum changes across versions.
 */
fun performPickupClickCompat(
    gameMode: MultiPlayerGameMode,
    containerId: Int,
    slotId: Int,
    buttonId: Int,
    player: Player
) {
    //? if >=26.1 {
    gameMode.handleContainerInput(containerId, slotId, buttonId, ClickAction.PICKUP, player)
    //?} else {
    /*gameMode.handleInventoryMouseClick(containerId, slotId, buttonId, ClickAction.PICKUP, player)
    *///?}
}


// ==========================================
// Current Screen Compat (26.2+)
// ==========================================
var Minecraft.currentScreen: Screen?
    get() = /*? if >=26.2 {*/ this.gui.screen() /*?} else {*/ /*this.screen *//*?}*/
    set(value) {
        /*? if >=26.1 {*/ this.setScreenAndShow(value!!) /*?} else {*/ /*this.setScreen(value) *//*?}*/
    }


// ==========================================
// Client Tick Event Compat (26.1+)
// ==========================================
fun registerClientEndTick(action: () -> Unit) {
    //? if >=26.1 {
    ClientTickEvents.END_LEVEL_TICK.register { action() }
    //?} else {
    /*ClientTickEvents.END_WORLD_TICK.register { action() }
    *///?}
}


// ==========================================
// Key Mapping Registration Compat (26.1+)
// ==========================================
fun registerKeyMappingCompat(mapping: KeyMapping): KeyMapping {
    //? if >=26.1 {
    return KeyMappingHelper.registerKeyMapping(mapping)
    //?} else {
    /*return KeyBindingHelper.registerKeyBinding(mapping)
    *///?}
}