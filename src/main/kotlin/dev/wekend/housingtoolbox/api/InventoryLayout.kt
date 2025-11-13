package dev.wekend.housingtoolbox.api

import dev.wekend.housingtoolbox.feature.data.Action

interface InventoryLayout {
    var name: String

    suspend fun getName(): String = name

    // TODO

    suspend fun delete()
}