package dev.wekend.mop.mixins.menu;

import dev.wekend.mop.Mop;
import dev.wekend.mop.config.MopSettings;
import net.minecraft.block.entity.SignText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractSignEditScreen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSignEditScreen.class)
public abstract class SignScreenMixin extends Screen {

    @Shadow
    public abstract void close();

    @Shadow
    private SignText text;

    @Shadow
    @Final
    private String[] messages;

    protected SignScreenMixin(Text title) {
        super(title);
    }



    @Inject(method = "keyPressed", at = @At("HEAD"))
    private void onEnterKeyPressed(KeyInput input, CallbackInfoReturnable<Boolean> cir) {
        if (!TextUtils.INSTANCE.isInputSign(this.text)) return;

        if (!MopSettings.Companion.getSignAutoConfirm().get()) return;
        if (!input.isEnter()) return;

        this.close();
    }

}