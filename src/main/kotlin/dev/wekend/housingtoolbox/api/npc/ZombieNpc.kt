package dev.wekend.housingtoolbox.api.npc

import dev.wekend.housingtoolbox.feature.data.ItemStack

// This has the baby option so I decided to put it under AnimalNpc
interface ZombieNpc: AnimalNpc {
    suspend fun getEquipment(): List<ItemStack>
    suspend fun setEquipment(newEquipment: List<ItemStack>)

    suspend fun isZombieVillager(): Boolean
    suspend fun setZombieVillager(isVillager: Boolean)
}