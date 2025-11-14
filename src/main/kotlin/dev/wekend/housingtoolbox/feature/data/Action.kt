@file:Suppress("SERIALIZER_TYPE_INCOMPATIBLE")

package dev.wekend.housingtoolbox.feature.data

import dev.wekend.housingtoolbox.feature.data.enums.Enchantment
import dev.wekend.housingtoolbox.feature.data.enums.Lobby
import dev.wekend.housingtoolbox.feature.data.enums.PotionEffect
import dev.wekend.housingtoolbox.feature.data.enums.Sound
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minecraft.nbt.NbtCompound

/*
Borrowed from https://github.com/sndyx/hsl, licensed under the MIT License
 */

sealed class Action(
    @Transient val actionName: String = ""
) {
    @Keyword("cancelEvent")
    class CancelEvent : Action("CANCEL_EVENT")


    data class Conditional(
        val conditions: List<Condition>,
        @SerialName("match_any_condition") val matchAnyCondition: Boolean,
        @SerialName("if_actions") val ifActions: List<Action>,
        @SerialName("else_actions") val elseActions: List<Action>,
    ) : Action("CONDITIONAL")


    @Keyword("changePlayerGroup")
    data class ChangePlayerGroup(
        @SerialName("new_group") val newGroup: String,
        @SerialName("include_higher_groups") val includeHigherGroups: Boolean = false,
    ) : Action("CHANGE_PLAYER_GROUP")


    @Keyword("kill")
    class KillPlayer : Action("KILL")


    @Keyword("fullHeal")
    class FullHeal : Action("FULL_HEAL")


    @Keyword("title")
    data class DisplayTitle(
        val title: String,
        val subtitle: String,
        @SerialName("fade_in") val fadeIn: Int,
        val stay: Int,
        @SerialName("fade_out") val fadeOut: Int,
    ) : Action("TITLE")


    @Keyword("actionBar")
    data class DisplayActionBar(val message: String) : Action("ACTION_BAR")


    @Keyword("resetInventory")
    class ResetInventory : Action("RESET_INVENTORY")


    @Keyword("changeMaxHealth")
    data class ChangeMaxHealth(
        @SerialName("mode") val op: StatOp,
        val amount: StatValue,
        val healOnChange: Boolean = true,
    ) : Action("CHANGE_MAX_HEALTH")

    @Keyword("parkCheck")
    class ParkourCheckpoint : Action("PARKOUR_CHECKPOINT")


    @Keyword("giveItem")
    data class GiveItem(
        val item: ItemStack,
        @SerialName("allow_multiple") val allowMultiple: Boolean,
        @SerialName("inventory_slot") val inventorySlot: StatValue,
        @SerialName("replace_existing_item") val replaceExistingItem: Boolean,
    ) : Action("GIVE_ITEM")


    @Keyword("removeItem")
    data class RemoveItem(val item: ItemStack) : Action("REMOVE_ITEM")


    @Keyword("chat")
    data class SendMessage(val message: String) : Action("SEND_MESSAGE")


    @Keyword("applyPotion")
    data class ApplyPotionEffect(
        val effect: PotionEffect,
        val duration: Int,
        val level: Int,
        @SerialName("override_existing_effects")
        val override: Boolean,
        @SerialName("show_potion_icon")
        val showIcon: Boolean,
    ) : Action("POTION_EFFECT")


    @Keyword("clearEffects")
    class ClearAllPotionEffects : Action("CLEAR_EFFECTS")


    @Keyword("xpLevel")
    data class GiveExperienceLevels(val levels: Int) : Action("GIVE_EXP_LEVELS")


    @Keyword("lobby")
    data class SendToLobby(val location: Lobby) : Action("SEND_TO_LOBBY")


    @Keyword("var")
    @Keyword("stat")
    data class PlayerVariable(
        val variable: String,
        val op: StatOp,
        val amount: StatValue,
        val unset: Boolean = false
    ) : Action("CHANGE_VARIABLE") {
        val holder = VariableHolder.Player
    }

    @Keyword("teamvar")
    @Keyword("teamstat")
    data class TeamVariable(
        val teamName: String,
        val variable: String,
        val op: StatOp,
        val amount: StatValue,
        val unset: Boolean = false
    ) : Action("CHANGE_VARIABLE") {
        val holder = VariableHolder.Team
    }

    @Keyword("globalvar")
    @Keyword("globalstat")
    data class GlobalVariable(
        val variable: String,
        val op: StatOp,
        val amount: StatValue,
        val unset: Boolean = false
    ) : Action("CHANGE_VARIABLE") {
        val holder = VariableHolder.Global
    }


    @Keyword("tp")
    data class TeleportPlayer(
        val location: Location,
        @SerialName("prevent_teleport_inside_blocks") val preventInsideBlocks: Boolean,
    ) : Action("TELEPORT_PLAYER")


    @Keyword("failParkour")
    data class FailParkour(val reason: String) : Action("FAIL_PARKOUR")


    @Keyword("sound")
    data class PlaySound(
        val sound: Sound,
        val volume: Double,
        val pitch: Double,
        val location: Location,
    ) : Action("PLAY_SOUND")


    @Keyword("compassTarget")
    data class SetCompassTarget(val location: Location) : Action("SET_COMPASS_TARGET")


    @Keyword("gamemode")
    data class SetGameMode(val gamemode: GameMode) : Action("SET_GAMEMODE")


    @Keyword("changeHealth")
    data class ChangeHealth(
        @SerialName("mode") val op: StatOp,
        val amount: StatValue,
    ) : Action("CHANGE_HEALTH")


    @Keyword("changeHunger")
    data class ChangeHunger(
        @SerialName("mode") val op: StatOp,
        val amount: StatValue,
    ) : Action("CHANGE_HUNGER")


    @Keyword("function")
    data class ExecuteFunction(val name: String, val global: Boolean) : Action("TRIGGER_FUNCTION")


    @Keyword("applyLayout")
    data class ApplyInventoryLayout(val layout: String) : Action("APPLY_LAYOUT")


    @Keyword("exit")
    class Exit : Action("EXIT")


    @Keyword("enchant")
    data class EnchantHeldItem(
        val enchantment: Enchantment,
        val level: Int,
    ) : Action("ENCHANT_HELD_ITEM")


    @Keyword("pause")
    data class PauseExecution(@SerialName("ticks_to_wait") val ticks: Int) : Action("PAUSE")


    @Keyword("setTeam")
    data class SetPlayerTeam(val team: String) : Action("SET_PLAYER_TEAM")


    @Keyword("displayMenu")
    data class DisplayMenu(val menu: String) : Action("DISPLAY_MENU")


    @Keyword("dropItem")
    data class DropItem(
        val item: ItemStack,
        val location: Location,
        @SerialName("drop_naturally") val dropNaturally: Boolean,
        @SerialName("prevent_item_merging") val disableMerging: Boolean,
        @SerialName("prioritize_player") val prioritizePlayer: Boolean,
        @SerialName("fallback_to_inventory") val inventoryFallback: Boolean,
        @SerialName("despawn_duraction_ticks") val despawnDurationTicks: Int,
        @SerialName("pickup_delay_ticks") val pickupDelayTicks: Int,
    ) : Action("DROP_ITEM")


    @Keyword("changeVelocity")
    data class ChangeVelocity(
        val x: StatValue,
        val y: StatValue,
        val z: StatValue,
    ) : Action("CHANGE_VELOCITY")


    @Keyword("launchTarget")
    data class LaunchToTarget(
        val location: Location,
        val strength: StatValue
    ) : Action("LAUNCH_TO_TARGET")


    @Keyword("playerWeather")
    data class SetPlayerWeather(val weather: Weather) : Action("SET_PLAYER_WEATHER")


    @Keyword("playerTime")
    data class SetPlayerTime(val time: Time) : Action("SET_PLAYER_TIME")


    @Keyword("displayNametag")
    data class ToggleNametagDisplay(@SerialName("display_nametag") val displayNametag: Boolean) :
        Action("TOGGLE_NAMETAG_DISPLAY")


    @Keyword("balanceTeam")
    class BalancePlayerTeam : Action("BALANCE_PLAYER_TEAM")


    @Keyword("closeMenu")
    class CloseMenu : Action("CLOSE_MENU")


    data class RandomAction(
        val actions: List<Action>,
    ) : Action("RANDOM_ACTION")


    @Keyword("consumeItem")
    class UseHeldItem : Action("USE_HELD_ITEM")

}

interface Keyed {
    val key: String
}

interface KeyedLabeled : Keyed {
    val label: String
}

object KeyedSerializer : KSerializer<Keyed> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Keyed", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Keyed {
        error("not implemented!")
    }

    override fun serialize(encoder: Encoder, value: Keyed) {
        encoder.encodeString(value.key)
    }
}

data class ItemStack(
    val nbt: NbtCompound? = null,
    val relativeFileLocation: String,
)

@Serializable
sealed class Location() {


    class Custom(
        val relX: Boolean,
        val relY: Boolean,
        val relZ: Boolean,
        val relPitch: Boolean = false,
        val relYaw: Boolean = false,
        val x: Double?,
        val y: Double?,
        val z: Double?,
        val pitch: Float?,
        val yaw: Float?,
    ) : Location()


    object HouseSpawn : Location()


    object CurrentLocation : Location()


    object InvokersLocation : Location()

}

enum class GameMode(override val key: String) : Keyed {
    Adventure("Adventure"),
    Survival("Survival"),
    Creative("Creative");

    companion object {
        fun fromKey(key: String): GameMode? = entries.find { it.key == key }
    }
}

enum class StatOp {
    @SerialName("SET")
    Set,

    @SerialName("INCREMENT")
    Inc,

    @SerialName("DECREMENT")
    Dec,

    @SerialName("MULTIPLY")
    Mul,

    @SerialName("DIVIDE")
    Div,
}

sealed class StatValue {
    data class I64(val value: Long) : StatValue()
    data class Dbl(val value: Double) : StatValue()
    data class Str(val value: String) : StatValue()
}

enum class Weather(override val key: String) : Keyed {
    SUNNY("Sunny"),
    RAINY("Rainy");

    companion object {
        fun fromKey(key: String): Weather? = entries.find { it.key == key }
    }
}

sealed class Time() {
    class Custom(
        val time: Long
    ) : Time()

    object ResetToWorldTime : Time()
    object Sunrise : Time()
    object Noon : Time()
    object Sunset : Time()
    object Midnight : Time()
}

enum class VariableHolder(override val key: String) : Keyed {
    Player("Player"),
    Global("Global"),
    Team("Team");
}