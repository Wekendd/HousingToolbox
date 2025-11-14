package dev.wekend.housingtoolbox.api.npc

import dev.wekend.housingtoolbox.feature.data.ItemStack

interface ArmorStandNpc: Npc {
    suspend fun getEquipment(): List<ItemStack>
    suspend fun setEquipment(newEquipment: List<ItemStack>)

    suspend fun hasArms(): Boolean
    suspend fun setArms(hasArms: Boolean)

    suspend fun hasBasePlate(): Boolean
    suspend fun setBasePlate(hasBasePlate: Boolean)

    suspend fun isVisible(): Boolean
    suspend fun setVisible(isVisible: Boolean)

    suspend fun isSmall(): Boolean
    suspend fun setSmall(isSmall: Boolean)
}