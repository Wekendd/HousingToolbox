package dev.wekend.housingtoolbox.api

import dev.wekend.housingtoolbox.feature.data.Action
import net.minecraft.item.Item

interface Function {
    var name: String
    suspend fun getName(): String = name
    suspend fun setName(newName: String)

    suspend fun getDescription(): String
    suspend fun setDescription(newDescription: String)

    suspend fun getIcon(): Item
    suspend fun setIcon(newIcon: Item)

    suspend fun getAutomaticExecution(): Int
    suspend fun setAutomaticExecution(newAutomaticExecution: Int)

    suspend fun getActions(): List<Action>
    suspend fun setActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun delete()
}