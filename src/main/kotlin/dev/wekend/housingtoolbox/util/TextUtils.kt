package dev.wekend.housingtoolbox.util

import net.minecraft.world.level.block.entity.SignText


object TextUtils {
    private val VALID_SIGN_TEMPLATES: List<List<String>> = listOf(
        listOf("^^^^^^", "Enter your", "search query!"),
        listOf("^^^^^^", "Enter a word", "to filter on!")
    )

    fun isInputSign(text: SignText): Boolean {
        val lines: List<String?> = text.getMessages(false).map { line ->
            if (line.siblings.isEmpty()) null else line.siblings[0].tryCollapseToString()
        }.subList(1, 4)

        return VALID_SIGN_TEMPLATES.stream().anyMatch { o: List<String> -> lines == o }
    }
}