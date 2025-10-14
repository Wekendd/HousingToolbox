package dev.wekend.housingtoolbox.keybind

import de.siphalor.amecs.api.AmecsKeyBinding
import de.siphalor.amecs.api.KeyModifiers
import de.siphalor.amecs.api.PriorityKeyBinding
import dev.wekend.housingtoolbox.HousingToolbox.MOD_ID
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.util.Identifier

object KeyBindings {

    fun init() {
        CommandShortcuts.init()
        KeyNav.init()
    }

    val housingCategory = KeyBinding.Category.create(Identifier.of(MOD_ID, "housing"))

    data class KeySpec(
        val id: String,
        val type: InputUtil.Type = InputUtil.Type.KEYSYM,
        val key: Int,
        val modifiers: KeyModifiers = KeyModifiers(),
        val onPress: () -> Boolean,
        val onRelease: () -> Boolean = { false }
    )

    fun register(spec: KeySpec): KeyBinding {
        return KeyBindingHelper.registerKeyBinding(
            object : AmecsKeyBinding(
                Identifier.of(MOD_ID, spec.id),
                spec.type,
                spec.key,
                housingCategory,
                spec.modifiers
            ), PriorityKeyBinding {
                override fun onPressedPriority() = spec.onPress()
                override fun onReleasedPriority() = spec.onRelease()
            }
        )
    }

    fun registerAll(specs: Iterable<KeySpec>) = specs.forEach { register(it) }
}