package dev.wekend.housingtoolbox.mixins.menu;

import dev.wekend.housingtoolbox.config.HousingToolboxSettings;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends ForgingScreen<AnvilScreenHandler> {

    public AnvilScreenMixin(AnvilScreenHandler handler, PlayerInventory playerInventory, Text title, Identifier texture) {
        super(handler, playerInventory, title, texture);
    }

    @Inject(method = "keyPressed", at = @At("HEAD"))
    private void onEnterKeyPressed(KeyInput input, CallbackInfoReturnable<Boolean> cir) {
        if (!HousingToolboxSettings.Companion.getAnvilEnterConfirm().get()) return;
        if (!input.isEnter()) return;

        if (!HousingToolboxSettings.Companion.getAnvilEnterConfirmGlobal().get()) {
            ItemStack item = this.handler.getSlot(0).getStack();
            if (item == null) return;

            if (item.getItem() != Items.PAPER) return;
            if (item.getName().getLiteralString() != null) return;
        }

        // Click output slot
        if (client == null || client.interactionManager == null) return;
        client.interactionManager.clickSlot(
                this.handler.syncId,
                2,
                0,
                SlotActionType.PICKUP,
                client.player
        );
    }

}
