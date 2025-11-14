package dev.wekend.housingtoolbox.util

import net.minecraft.client.MinecraftClient
import net.minecraft.component.DataComponentTypes
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtOps
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket
import kotlin.jvm.optionals.getOrNull

object ItemUtils {
    fun ItemStack.loreLine(line: Int, color: Boolean, filter: (String) -> Boolean = { true }): String {
        val lore = this.get(DataComponentTypes.LORE) ?: return ""
        if (lore.lines.size <= line) return ""
        val loreLine = lore.lines[line]
        val loreString = TextUtils.convertTextToString(loreLine, color)
        return if (filter(loreString)) loreString else ""
    }

    fun ItemStack.loreLine(color: Boolean, filter: (String) -> Boolean = { true }): String? {
        val lore = this.get(DataComponentTypes.LORE) ?: return null
        for (lore in lore.lines) {
            val loreString = TextUtils.convertTextToString(lore, color)
            if (filter(loreString)) return loreString
        }
        return null
    }

    fun ItemStack.loreLineWithIndex(color: Boolean, filter: (String) -> Boolean = { true }): Pair<String, Int>? {
        val lore = this.get(DataComponentTypes.LORE) ?: return null
        for ((index, lore) in lore.lines.withIndex()) {
            val loreString = TextUtils.convertTextToString(lore, color)
            if (filter(loreString)) return Pair(loreString, index)
        }
        return null
    }

    fun createFromNBT(nbt: NbtCompound): ItemStack {
        return ItemStack.CODEC.decode(NbtOps.INSTANCE, nbt).result().getOrNull()?.first
            ?: throw IllegalArgumentException("Failed to decode ItemStack from NBT")
    }

    fun placeInventory(itemStack: ItemStack, slot: Int) {
        val player = MinecraftClient.getInstance().player ?: return
        val packet = CreativeInventoryActionC2SPacket(slot, itemStack)
        player.networkHandler.sendPacket(packet)
    }
}