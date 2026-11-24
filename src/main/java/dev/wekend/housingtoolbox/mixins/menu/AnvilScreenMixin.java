package dev.wekend.housingtoolbox.mixins.menu;

import com.mojang.blaze3d.platform.InputConstants;
import dev.wekend.housingtoolbox.config.HousingToolboxSettings;
import dev.wekend.housingtoolbox.util.CompatKt;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends ItemCombinerScreen<AnvilMenu> {

    public AnvilScreenMixin(AnvilMenu menu, Inventory playerInventory, Component title, Identifier texture) {
        super(menu, playerInventory, title, texture);
    }

    @Inject(method = "keyPressed", at = @At("HEAD"))
    private void onEnterKeyPressed(KeyEvent keyEvent, CallbackInfoReturnable<Boolean> cir) {
        if (!HousingToolboxSettings.Companion.getAnvilEnterConfirm().get()) return;

        int keyCode = keyEvent.key();

        if (keyCode != InputConstants.KEY_RETURN) return;

        if (!HousingToolboxSettings.Companion.getAnvilEnterConfirmGlobal().get()) {
            ItemStack item = this.menu.getSlot(0).getItem();
            if (item.isEmpty()) return;
            if (!item.is(Items.PAPER)) return;
        }

        if (this.minecraft.gameMode == null || this.minecraft.player == null) return;

        CompatKt.performPickupClickCompat(
                this.minecraft.gameMode,
                this.menu.containerId,
                2,
                0,
                this.minecraft.player
        );
    }
}