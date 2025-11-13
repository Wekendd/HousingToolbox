package dev.wekend.housingtoolbox.api.npc

import dev.wekend.housingtoolbox.feature.data.ItemStack

interface PlayerNpc: Npc {
    suspend fun getSize(): Int
    suspend fun setSize(newSize: Int)
}