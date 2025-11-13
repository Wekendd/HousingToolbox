package dev.wekend.housingtoolbox.api.npc

interface SlimeNpc: Npc {
    suspend fun getSize(): Int
    suspend fun setSize(newSize: Int)
}