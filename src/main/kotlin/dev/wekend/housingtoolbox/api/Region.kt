package dev.wekend.housingtoolbox.api

import dev.wekend.housingtoolbox.feature.data.Action

interface Region {
    var name: String

    suspend fun getName(): String = name
    suspend fun setName(newName: String)

    suspend fun getPvpSettings(): MutableMap<PvpSettings, Boolean>
    suspend fun setPvpSettings(newPvpSettings: MutableMap<PvpSettings, Boolean>)

    suspend fun getEntryActions(): List<Action>
    suspend fun setEntryActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getExitActions(): List<Action>
    suspend fun setExitActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun delete()

    enum class PvpSettings {
        PVP,
        DOUBLE_JUMP,
        FIRE_DAMAGE,
        FALL_DAMAGE,
        POISON_WITHER_DAMAGE,
        SUFFOCATION,
        HUNGER,
        NATURAL_REGENERATION,
        DEATH_MESSAGES,
        INSTANT_RESPAWN,
        KEEP_INVENTORY,
    }
}