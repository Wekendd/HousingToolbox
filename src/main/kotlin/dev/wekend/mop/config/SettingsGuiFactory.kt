package dev.wekend.mop.config

import dev.isxander.yacl3.api.*
import dev.isxander.yacl3.config.v3.register
import dev.isxander.yacl3.dsl.*
import net.minecraft.client.gui.screen.Screen

fun createSettingsGui(parent: Screen?) = SettingsGuiFactory().createSettingsGui(parent)

private class SettingsGuiFactory {
    val settings = MopSettings(MopSettings)

    fun createSettingsGui(parent: Screen?) = YetAnotherConfigLib("mop") {
        save(MopSettings::saveToFile)

        val input by categories.registering {
            val anvil by groups.registering {
                options.register(MopSettings.anvilAutoConfirm) {
                    defaultDescription()
                    controller = tickBox()
                }
            }

            val sign by groups.registering {
                options.register(MopSettings.signAutoConfirm) {
                    defaultDescription()
                    controller = tickBox()
                }
            }

            val chat by groups.registering {
                options.register(MopSettings.chatAutoOpen) {
                    defaultDescription()
                    controller = tickBox()
                }
                options.register(MopSettings.chatIncludePrevious) {
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