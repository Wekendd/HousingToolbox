package dev.wekend.housingtoolbox.mixins.menu;

import dev.wekend.housingtoolbox.feature.ChatInput;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ScreenCloseMixin extends ClientCommonNetworkHandler implements ClientPlayPacketListener, TickablePacketListener {
    protected ScreenCloseMixin(MinecraftClient client, ClientConnection connection, ClientConnectionState connectionState) {
        super(client, connection, connectionState);
    }

    @Inject(method = "onCloseScreen", at = @At("HEAD"), cancellable = true)
    private void onCloseScreen(CloseScreenS2CPacket packet, CallbackInfo ci) {
        if (ChatInput.IgnoreCloseScreens.INSTANCE.shouldIgnore()) ci.cancel();
    }

}
