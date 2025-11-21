package dev.wekend.housingtoolbox.mixins.menu;

import com.mojang.brigadier.suggestion.Suggestions;
import dev.wekend.housingtoolbox.config.HousingToolboxSettings;
import dev.wekend.housingtoolbox.util.CommandUtils;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onCommandSuggestions", at = @At("HEAD"))
    private void onCommandSuggestions(CommandSuggestionsS2CPacket packet, CallbackInfo ci) {
        Suggestions suggestions = packet.getSuggestions();
        CommandUtils.INSTANCE.handleSuggestions(suggestions);
    }

}
