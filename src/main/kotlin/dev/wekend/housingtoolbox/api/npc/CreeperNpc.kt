package dev.wekend.housingtoolbox.api.npc

interface CreeperNpc: Npc {
    suspend fun setCharged(isCharged: Boolean)
    suspend fun isCharged(): Boolean
}