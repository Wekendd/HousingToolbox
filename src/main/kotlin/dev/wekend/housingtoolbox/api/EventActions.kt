package dev.wekend.housingtoolbox.api

import dev.wekend.housingtoolbox.feature.data.Action
import net.minecraft.item.Item

interface EventActions {
    enum class Events {
        PLAYER_JOIN,
        PLAYER_QUIT,
        PLAYER_DEATH,
        PLAYER_KILL,
        PLAYER_RESPAWN,
        GROUP_CHANGE,
        PVP_STATE_CHANGE,
        FISH_CATCH,
        PLAYER_ENTER_PORTAL,
        PLAYER_DAMAGE,
        PLAYER_BLOCK_BREAK,
        START_PARKOUR,
        COMPLETE_PARKOUR,
        PLAYER_DROP_ITEM,
        PLAYER_PICKUP_ITEM,
        PLAYER_CHANGE_HELD_ITEM,
        PLAYER_TOGGLE_SNEAK,
        PLAYER_TOGGLE_FLY
    }

    suspend fun getActionsForEvent(event: Events): List<Action>
    suspend fun setActionsForEvent(event: Events, newActions: List<Action>, optimized: Boolean = false)
}