package dev.wekend.mop.config

import com.mojang.serialization.Codec
import dev.isxander.yacl3.config.v3.JsonFileCodecConfig
import dev.isxander.yacl3.config.v3.register
import dev.isxander.yacl3.config.v3.value
import net.fabricmc.loader.api.FabricLoader

open class MopSettings(): JsonFileCodecConfig<MopSettings>(
    FabricLoader.getInstance().configDir.resolve("mop.json")
) {
    val anvilEnterConfirm by register<Boolean>(default = true, Codec.BOOL)
    val anvilEnterConfirmGlobal by register<Boolean>(default = false, Codec.BOOL)

    val signEnterConfirm by register<Boolean>(default = true, Codec.BOOL)

    val chatAutoOpen by register<Boolean>(default = true, Codec.BOOL)
    val chatIncludePrevious by register<Boolean>(default = true, Codec.BOOL)

    var firstLaunch = false
    val _firstLaunch by register<Boolean>(default = true, Codec.BOOL)

    final val allSettings = arrayOf(
        anvilEnterConfirm,
        anvilEnterConfirmGlobal,
        signEnterConfirm,
        chatAutoOpen,
        _firstLaunch
    )

    constructor(settings: MopSettings) : this() {
        this.anvilEnterConfirm.value = settings.anvilEnterConfirm.value
        this.anvilEnterConfirmGlobal.value = settings.anvilEnterConfirmGlobal.value
        this.signEnterConfirm.value = settings.signEnterConfirm.value
        this.chatAutoOpen.value = settings.chatAutoOpen.value
        this.chatIncludePrevious.value = settings.chatIncludePrevious.value
        this._firstLaunch.value = settings._firstLaunch.value
    }

    companion object : MopSettings() {
        init {
            if (!loadFromFile()) {
                saveToFile()
            }

            if (_firstLaunch.value) {
                firstLaunch = true
                _firstLaunch.value = false
                saveToFile()
            }
        }
    }
}