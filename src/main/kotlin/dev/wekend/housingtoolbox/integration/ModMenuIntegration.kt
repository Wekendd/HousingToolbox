package dev.wekend.housingtoolbox.integration

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import dev.wekend.housingtoolbox.config.createSettingsGui

object ModMenuIntegration: ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> = ConfigScreenFactory { parent ->
        createSettingsGui(parent)
    }
}