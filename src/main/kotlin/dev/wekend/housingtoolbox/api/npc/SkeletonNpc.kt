package dev.wekend.housingtoolbox.api.npc

import dev.wekend.housingtoolbox.feature.data.ItemStack

interface SkeletonNpc: Npc {
    suspend fun getEquipment(): List<ItemStack>
    suspend fun setEquipment(newEquipment: List<ItemStack>)

    suspend fun getWithered(): Boolean
    suspend fun setWithered(newWithered: Boolean)
}