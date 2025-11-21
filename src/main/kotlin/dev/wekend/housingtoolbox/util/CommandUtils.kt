package dev.wekend.housingtoolbox.util

import com.mojang.brigadier.suggestion.Suggestions
import dev.wekend.housingtoolbox.HousingToolbox.MC
import kotlinx.coroutines.future.await
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import net.minecraft.client.MinecraftClient
import net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket
import net.minecraft.util.packrat.Suggestable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

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

    private var pendingCompletion: CompletableFuture<List<String>>? = null
    suspend fun getTabCompletionData(baseCommand: String): List<String> {
        val net = MC.networkHandler ?: return emptyList()

        val partialCommand = buildString {
            append(if (baseCommand.startsWith('/')) baseCommand else "/$baseCommand")
            if (!endsWith(' ')) append(' ')
        }

        val deferred = CompletableFuture<List<String>>()
        pendingCompletion = deferred

        net.sendPacket(RequestCommandCompletionsC2SPacket(-1, partialCommand))

        val result = withTimeoutOrNull(1000) {
            try {
                deferred.await()
            } finally {
                pendingCompletion = null
            }
        }

        //Probably not necessary, but I dont want false negatives
        if (result == null) {
            pendingCompletion = null
        }

        return result ?: emptyList() // FIXME Error out or something when it timesout
    }

    fun handleSuggestions(suggestions: Suggestions) {
        if (pendingCompletion != null && !pendingCompletion!!.isDone) pendingCompletion!!.complete(suggestions.list.map { it.text })
    }
}