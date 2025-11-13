package dev.wekend.housingtoolbox.util

import net.minecraft.client.MinecraftClient
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

object CommandUtils {
    fun runCommand(command: String, delay: Long = 0L) {
        if (delay == 0L) {
            MinecraftClient.getInstance().player?.networkHandler?.sendChatCommand(command)
        } else {
            CompletableFuture.runAsync({

                MinecraftClient.getInstance().player?.networkHandler?.sendChatCommand(command)

            }, CompletableFuture.delayedExecutor(delay, TimeUnit.MILLISECONDS))
        }
    }
}