package dev.wekend.housingtoolbox.keybind

import dev.wekend.housingtoolbox.HousingToolbox.MOD_ID
import net.minecraft.client.KeyMapping
import com.mojang.blaze3d.platform.InputConstants
import de.siphalor.amecs.key_modifiers.api.AmecsKeyMappingWithKeyModifiers
import de.siphalor.amecs.key_modifiers.api.AmecsKeyModifierCombination
import de.siphalor.amecs.priority_key_mappings.api.AmecsPriorityKeyMapping
import dev.wekend.housingtoolbox.util.registerKeyMappingCompat
import net.minecraft.resources.Identifier

object KeyBindings {

    fun init() {
        CommandShortcuts.init()
        KeyNav.init()
    }

    val housingCategory = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "housing"))

    data class KeySpec(
        val id: String,
        val type: InputConstants.Type = InputConstants.Type.KEYSYM,
        val key: Int,
        val modifiers: AmecsKeyModifierCombination = AmecsKeyModifierCombination(),
        val onPress: () -> Boolean,
        val onRelease: () -> Boolean = { false }
    )

    fun register(spec: KeySpec): KeyMapping {
        return registerKeyMappingCompat(
            object: AmecsKeyMappingWithKeyModifiers(
                Identifier.fromNamespaceAndPath(MOD_ID, spec.id),
                spec.type,
                spec.key,
                housingCategory,
                spec.modifiers
            ), AmecsPriorityKeyMapping {
                override fun onPressedPriority() = spec.onPress()
                override fun onReleasedPriority() = spec.onRelease()
            }
        )
    }

    fun registerAll(specs: Iterable<KeySpec>) = specs.forEach { register(it) }
}