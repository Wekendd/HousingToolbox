package dev.wekend.housingtoolbox.mixins.menu;

import com.mojang.blaze3d.platform.InputConstants;
import dev.wekend.housingtoolbox.config.HousingToolboxSettings;
import dev.wekend.housingtoolbox.util.TextUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.SignText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSignEditScreen.class)
public abstract class SignScreenMixin extends Screen {

    @Shadow
    public abstract void onClose();

    //? if >=26.3 {
    @Shadow
    private SignText.Mutable text;
    //?} else {
    /*@Shadow
    private SignText text;
    *///?}

    @Shadow
    @Final
    private String[] messages;

    protected SignScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "keyPressed", at = @At("HEAD"))
    private void onEnterKeyPressed(KeyEvent keyEvent, CallbackInfoReturnable<Boolean> cir) {
        //? if >=26.3 {
        var text = this.text.asImmutable();
        //?} else {
        /*var text = this.text;
        *///?}

        if (!TextUtils.INSTANCE.isInputSign(text)) return;
        if (!HousingToolboxSettings.Companion.getSignEnterConfirm().get()) return;

        int keyCode = keyEvent.key();

        if (keyCode != InputConstants.KEY_RETURN) return;

        this.onClose(); // this.close() -> this.onClose()
    }
}