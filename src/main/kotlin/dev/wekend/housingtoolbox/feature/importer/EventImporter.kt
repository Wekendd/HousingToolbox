package dev.wekend.housingtoolbox.feature.importer

import dev.wekend.housingtoolbox.api.Event
import dev.wekend.housingtoolbox.feature.data.Action
import dev.wekend.housingtoolbox.util.CommandUtils
import dev.wekend.housingtoolbox.util.MenuUtils

internal class EventImporter : Event {
    private fun openEventActionsMenu(event: Event.Events) {
        CommandUtils.runCommand("eventactions")
        MenuUtils.clickMenuSlot(event.item)
    }

    override suspend fun getActionsForEvent(event: Event.Events): List<Action> {
        TODO("Not yet implemented")
    }

    override suspend fun setActionsForEvent(event: Event.Events, newActions: List<Action>, optimized: Boolean) {
        openEventActionsMenu(event)
        ActionInteraction("Edit Actions").addActions(newActions)
    }
}