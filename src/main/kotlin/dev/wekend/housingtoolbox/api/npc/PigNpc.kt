package dev.wekend.housingtoolbox.api.npc

interface PigNpc: AnimalNpc {
    suspend fun getSaddled(): Boolean
    suspend fun setSaddled(newSaddled: Boolean)
}