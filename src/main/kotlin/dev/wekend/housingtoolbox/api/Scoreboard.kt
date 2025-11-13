package dev.wekend.housingtoolbox.api

import dev.wekend.housingtoolbox.feature.data.Action
import net.minecraft.item.Item

interface Function {
    suspend fun getLines(): List<String>
    suspend fun setLines(newLines: List<String>)
}