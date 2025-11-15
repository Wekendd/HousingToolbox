package dev.wekend.housingtoolbox.api.npc

interface SizedNpc: Npc {
    suspend fun getSize(): Int
    suspend fun setSize(newSize: Int)
}