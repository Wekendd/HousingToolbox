package dev.wekend.housingtoolbox.api

import dev.wekend.housingtoolbox.feature.data.Action
import dev.wekend.housingtoolbox.feature.data.ItemStack
import net.minecraft.item.Item

interface Npc {
    var name: String
    suspend fun getName(): String = name
    suspend fun setName(newName: String)

//    TODO: Implement npc types and... their different special settings...
//    suspend fun getSkin(): String
//    suspend fun setSkin(newSkin: String)

//    TODO: implement this but good
//    suspend fun getEquipment(): List<ItemStack>
//    suspend fun setEquipment(newEquipment: List<ItemStack>)

    suspend fun getLookAtPlayers(): Boolean
    suspend fun setLookAtPlayers(newLookAtPlayers: Boolean)

    suspend fun getHideNameTag(): Boolean
    suspend fun setHideNameTag(newHideNameTag: Boolean)

    suspend fun getLeftClickActions(): List<Action>
    suspend fun setLeftClickActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun getRightClickActions(): List<Action>
    suspend fun setRightClickActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun delete()
}