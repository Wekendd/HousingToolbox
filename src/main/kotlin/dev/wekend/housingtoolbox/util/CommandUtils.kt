package dev.wekend.housingtoolbox.util

import com.mojang.brigadier.suggestion.Suggestions
import dev.wekend.housingtoolbox.HousingToolbox.MC
import kotlinx.coroutines.future.await
import kotlinx.coroutines.withTimeout
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

    private val completionIdCounter = AtomicInteger(1)
    private val pendingCompletions = ConcurrentHashMap<Int, CompletableFuture<List<String>>>()
    suspend fun getTabCompletionData(baseCommand: String): List<String> = withTimeout(1000) {
        val net = MC.networkHandler ?: return@withTimeout emptyList()

        val partialCommand = buildString {
            append(if (baseCommand.startsWith('/')) baseCommand else "/$baseCommand")
            if (!endsWith(' ')) append(' ')
        }

        val id = completionIdCounter.getAndIncrement()
        val deferred = CompletableFuture<List<String>>()
        pendingCompletions[id] = deferred

        net.sendPacket(RequestCommandCompletionsC2SPacket(id, partialCommand))

        try {
            deferred.await()
        } finally {
            pendingCompletions.remove(id)
        }
    }

    fun handleSuggestions(id: Int, suggestions: Suggestions) {
        val deferred = pendingCompletions[id] ?: return
        if (!deferred.isDone) deferred.complete(suggestions.list.map { it.text })
    }
}