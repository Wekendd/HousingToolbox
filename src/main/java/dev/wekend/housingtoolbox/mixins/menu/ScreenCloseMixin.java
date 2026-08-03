package dev.wekend.housingtoolbox.mixins.menu;

import dev.wekend.housingtoolbox.feature.ChatInput;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ScreenCloseMixin {

    @Inject(method = "handleContainerClose", at = @At("HEAD"), cancellable = true)
    private void onHandleContainerClose(ClientboundContainerClosePacket packet, CallbackInfo ci) {
        if (ChatInput.IgnoreCloseScreens.INSTANCE.shouldIgnore()) ci.cancel();
    }

}