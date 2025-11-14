package dev.wekend.housingtoolbox.api.npc

import dev.wekend.housingtoolbox.feature.data.ItemStack

// Like zombie, has a baby option so its under AnimalNpc
interface ZombiePigmanNpc: AnimalNpc {
    suspend fun getEquipment(): List<ItemStack>
    suspend fun setEquipment(newEquipment: List<ItemStack>)
}