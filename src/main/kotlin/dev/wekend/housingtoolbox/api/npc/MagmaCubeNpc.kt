package dev.wekend.housingtoolbox.api.npc

interface MagmaCubeNpc: Npc {
    suspend fun getSize(): Int
    suspend fun setSize(newSize: Int)
}