package dev.wekend.housingtoolbox.api

import dev.wekend.housingtoolbox.feature.data.Action

interface Team {
    var name: String

    suspend fun getName(): String = name
    suspend fun setName(newName: String)

    suspend fun getMenuSize(): Int
    suspend fun setMenuSize(newSize: Int)

//    TODO: implement menu elements
//    suspend fun getMenuElements(): List<Action>
//    suspend fun setMenuElements(newMenuElements: List<Action>)

    suspend fun delete()
}