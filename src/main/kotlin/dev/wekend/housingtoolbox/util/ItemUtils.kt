package dev.wekend.housingtoolbox.util

import net.minecraft.component.DataComponentTypes
import net.minecraft.item.ItemStack

object ItemUtils {
    fun ItemStack.loreLine(line: Int, filter: (String) -> Boolean): String {
        val lore = this.get(DataComponentTypes.LORE) ?: return ""
        if (lore.lines.size <= line) return ""
        val loreLine = lore.lines[line]
        val loreString = TextUtils.convertTextToString(loreLine)
        return if (filter(loreString)) loreString else ""
    }
}