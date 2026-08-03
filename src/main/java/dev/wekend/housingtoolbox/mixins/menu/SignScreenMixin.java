package dev.wekend.housingtoolbox.mixins.menu;

import dev.wekend.housingtoolbox.config.HousingToolboxSettings;
import dev.wekend.housingtoolbox.util.TextUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.SignText;
import org.lwjgl.glfw.GLFW;
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

    @Shadow
    private SignText text;

    @Shadow
    @Final
    private String[] messages;

    protected SignScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "keyPressed", at = @At("HEAD"))
    private void onEnterKeyPressed(KeyEvent keyEvent, CallbackInfoReturnable<Boolean> cir) {
        if (!TextUtils.INSTANCE.isInputSign(this.text)) return;
        if (!HousingToolboxSettings.Companion.getSignEnterConfirm().get()) return;

        int keyCode = keyEvent.key();

        if (keyCode != GLFW.GLFW_KEY_ENTER && keyCode != GLFW.GLFW_KEY_KP_ENTER) return;

        this.onClose(); // this.close() -> this.onClose()
    }
}