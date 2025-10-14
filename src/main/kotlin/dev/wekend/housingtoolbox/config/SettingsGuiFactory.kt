package dev.wekend.housingtoolbox.config

import dev.isxander.yacl3.config.v3.register
import dev.isxander.yacl3.dsl.*
import dev.wekend.housingtoolbox.HousingToolbox
import net.minecraft.client.gui.screen.Screen

fun createSettingsGui(parent: Screen?) = SettingsGuiFactory().createSettingsGui(parent)

private class SettingsGuiFactory {
    val settings = HousingToolboxSettings(HousingToolboxSettings)

    fun createSettingsGui(parent: Screen?) = YetAnotherConfigLib(HousingToolbox.MOD_ID) {
        save(HousingToolboxSettings::saveToFile)

        val input by categories.registering {
            val anvil by groups.registering {
                options.register(HousingToolboxSettings.anvilEnterConfirm) {
                    defaultDescription()
                    controller = tickBox()
                }
                options.register(HousingToolboxSettings.anvilEnterConfirmGlobal) {
                    defaultDescription()
                    controller = tickBox()
                }
            }

            val sign by groups.registering {
                options.register(HousingToolboxSettings.signEnterConfirm) {
                    defaultDescription()
                    controller = tickBox()
                }
            }

            val chat by groups.registering {
                options.register(HousingToolboxSettings.chatAutoOpen) {
                    defaultDescription()
                    controller = tickBox()
                }
                options.register(HousingToolboxSettings.chatIncludePrevious) {
                    defaultDescription()
                    controller = tickBox()
                }
            }
        }
    }.generateScreen(parent)

}

private fun OptionDsl<*>.defaultDescription() {
    descriptionBuilder {
        addDefaultText()
    }
}

private fun ButtonOptionDsl.defaultDescription() {
    descriptionBuilder {
        addDefaultText()
    }
}