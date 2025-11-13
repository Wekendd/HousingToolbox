package dev.wekend.housingtoolbox.api

import dev.wekend.housingtoolbox.feature.data.Action
import net.minecraft.item.Item

interface Function {
    suspend fun getPlayerJoinActions(): List<Action>
    suspend fun setPlayerJoinActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getPlayerQuitActions(): List<Action>
    suspend fun setPlayerQuitActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getPlayerDeathActions(): List<Action>
    suspend fun setPlayerDeathActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getPlayerKillActions(): List<Action>
    suspend fun setPlayerKillActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getPlayerRespawnActions(): List<Action>
    suspend fun setPlayerRespawnActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getGroupChangeActions(): List<Action>
    suspend fun setGroupChangeActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getPvpStateChangeActions(): List<Action>
    suspend fun setPvpStateChangeActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getFishCaughtActions(): List<Action>
    suspend fun setFishCaughtActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getPlayerEnterPortalActions(): List<Action>
    suspend fun setPlayerEnterPortalActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getPlayerDamageActions(): List<Action>
    suspend fun setPlayerDamageActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getPlayerBlockBreakActions(): List<Action>
    suspend fun setPlayerBlockBreakActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getStartParkourActions(): List<Action>
    suspend fun setStartParkourActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getCompleteParkourActions(): List<Action>
    suspend fun setCompleteParkourActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getPlayerDropItemActions(): List<Action>
    suspend fun setPlayerDropItemActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getPlayerPickUpItemActions(): List<Action>
    suspend fun setPlayerPickUpItemActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getPlayerChangeHeldItemActions(): List<Action>
    suspend fun setPlayerChangeHeldItemActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getPlayerToggleSneakActions(): List<Action>
    suspend fun setPlayerToggleSneakActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getPlayerToggleFlightActions(): List<Action>
    suspend fun setPlayerToggleFlightActions(newActions: List<Action>, optimized: Boolean = false)
}