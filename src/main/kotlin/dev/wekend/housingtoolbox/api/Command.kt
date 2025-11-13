package dev.wekend.housingtoolbox.api

import dev.wekend.housingtoolbox.feature.data.Action

interface Command {
    var name: String

    suspend fun getName(): String = name
    suspend fun setName(newName: String)

    suspend fun getCommandMode(): CommandMode
    suspend fun setCommandMode(newCommandMode: CommandMode)

    suspend fun getRequiredGroupPriority(): Int
    suspend fun setRequiredGroupPriority(newPriority: Int)

    suspend fun getListed(): Boolean
    suspend fun setListed(newListed: Boolean)

    suspend fun getActions(): List<Action>
    suspend fun setActions(newActions: List<Action>, optimized: Boolean = false)

    suspend fun delete()

    enum class CommandMode {
        SELF,
        TARGETED
    }
}