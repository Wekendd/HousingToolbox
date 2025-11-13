package dev.wekend.housingtoolbox.api.npc

import dev.wekend.housingtoolbox.feature.data.ItemStack

interface PlayerNpc: Npc {
    suspend fun getSkin(): String
    suspend fun setSkin(newSkin: String)

    suspend fun getEquipment(): List<net.minecraft.item.ItemStack>
    suspend fun setEquipment(newEquipment: List<ItemStack>)
}