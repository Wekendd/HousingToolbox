package dev.wekend.housingtoolbox.api

import dev.wekend.housingtoolbox.feature.data.Action
import dev.wekend.housingtoolbox.util.MenuUtils.MenuSlot
import net.minecraft.item.ItemStack

interface Menu {
    var title: String

    suspend fun createIfNotExists(): Boolean

    suspend fun getTitle(): String = title
    suspend fun setTitle(newTitle: String)

    suspend fun getMenuSize(): Int
    suspend fun changeMenuSize(newSize: Int)

    suspend fun getMenuElements(): Array<ItemStack>
    suspend fun setMenuElements(newMenuElements: Array<ItemStack>)

    suspend fun delete()
}