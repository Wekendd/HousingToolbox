package dev.wekend.mop.utils

import dev.wekend.mop.Mop
import net.minecraft.block.entity.SignText
import net.minecraft.text.Text
import java.util.Arrays


object TextUtils {
    private val VALID_SIGN_TEMPLATES: List<List<String>> = listOf(
        listOf("^^^^^^", "Enter your", "search query!")
    )

    fun isInputSign(text: SignText): Boolean {
        val lines: List<String?> = text.getMessages(false).map { line ->
            if (line.siblings.isEmpty()) null else line.siblings?.get(0)?.literalString
        }.subList(1, 4)

        return VALID_SIGN_TEMPLATES.stream().anyMatch { o: List<String> -> lines == o }
    }
}