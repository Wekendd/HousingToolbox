@file:Suppress("SERIALIZER_TYPE_INCOMPATIBLE")

package dev.wekend.housingtoolbox.feature.data

import dev.wekend.housingtoolbox.feature.data.enums.Permission
import dev.wekend.housingtoolbox.feature.data.enums.PotionEffect
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/*
Borrowed from https://github.com/sndyx/hsl, licensed under the MIT License
 */

@Serializable
sealed class Condition(
    @Transient val conditionName: String = ""
) {
    var inverted = false

    data class RequiredGroup(
        @SerialName("required_group")
        val group: String,
        @SerialName("include_higher_groups")
        val includeHigherGroups: Boolean,
    ) : Condition("IN_GROUP")

    data class PlayerVariableRequirement(
        val variable: String,
        val op: Comparison,
        val value: StatValue,
    ) : Condition("VARIABLE_REQUIREMENT") {
        val holder = VariableHolder.Player
    }

    data class TeamVariableRequirement(
        val team: String?,
        val variable: String,
        val op: Comparison,
        val value: StatValue,
    ) : Condition("VARIABLE_REQUIREMENT") {
        val holder = VariableHolder.Team
    }

    data class GlobalVariableRequirement(
        val variable: String,
        val op: Comparison,
        val value: StatValue,
    ) : Condition("VARIABLE_REQUIREMENT") {
        val holder = VariableHolder.Global
    }

    data class HasPermission(
        @SerialName("required_permission")
        val permission: Permission,
    ) : Condition("HAS_PERMISSION")

    data class InRegion(
        val region: String,
    ) : Condition("IN_REGION")

    data class HasItem(
        val item: ItemStack,
        @SerialName("what_to_check") val whatToCheck: ItemCheck,
        @SerialName("where_to_check") val whereToCheck: InventoryLocation,
        @SerialName("required_amount") val amount: ItemAmount,
    ) : Condition("HAS_ITEM")

    data object InParkour : Condition("IN_PARKOUR")

    data class RequiredEffect(
        val effect: PotionEffect,
    ) : Condition("POTION_EFFECT")

    data object PlayerSneaking : Condition("SNEAKING")

    data object PlayerFlying : Condition("FLYING")

    data class RequiredHealth(
        val mode: Comparison,
        val amount: StatValue,
    ) : Condition("HEALTH")

    data class RequiredMaxHealth(
        val mode: Comparison,
        val amount: StatValue,
    ) : Condition("MAX_HEALTH")

    data class RequiredHungerLevel(
        val mode: Comparison,
        val amount: StatValue,
    ) : Condition("HUNGER_LEVEL")

    data class RequiredGameMode(
        @SerialName("required_gamemode")
        val gameMode: GameMode
    ) : Condition("GAMEMODE")

    data class RequiredPlaceholderNumber(
        val placeholder: String,
        val mode: Comparison,
        val amount: StatValue,
    ) : Condition("PLACEHOLDER_NUMBER")

    data class RequiredTeam(
        @SerialName("required_team")
        val team: String,
    ) : Condition("IN_TEAM")

    data object PvpEnabled : Condition("PVP_ENABLED")

    data class FishingEnvironment(
        val environment: dev.wekend.housingtoolbox.feature.data.FishingEnvironment
    ) : Condition("FISHING_ENVIRONMENT")

    data class PortalType(
        @SerialName("portal_type")
        val type: dev.wekend.housingtoolbox.feature.data.PortalType
    ) : Condition("PORTAL_TYPE")

    data class DamageCause(
        val cause: dev.wekend.housingtoolbox.feature.data.DamageCause
    ) : Condition("DAMAGE_CAUSE")

    data class RequiredDamageAmount(
        val mode: Comparison,
        val amount: StatValue,
    ) : Condition("DAMAGE_AMOUNT")

    data class BlockType(
        val item: ItemStack,
        @SerialName("match_type_only")
        val matchTypeOnly: Boolean,
    ) : Condition("BLOCK_TYPE")

    data class IsItem(
        val item: ItemStack,
        @SerialName("what_to_check") val whatToCheck: ItemCheck,
        @SerialName("where_to_check") val whereToCheck: InventoryLocation,
        @SerialName("required_amount") val amount: ItemAmount,
    ) : Condition("IS_ITEM")
}

enum class Comparison {
    @SerialName("EQUAL")
    Eq,
    @SerialName("GREATER_THAN")
    Gt,
    @SerialName("GREATER_THAN_OR_EQUAL")
    Ge,
    @SerialName("LESS_THAN")
    Lt,
    @SerialName("LESS_THAN_OR_EQUAL")
    Le;
}

enum class ItemCheck(override val key: String) : Keyed {
    ItemType("Item Type"),
    Metadata("Metadata");

    companion object {
        fun fromKey(key: String): ItemCheck? {
            return entries.find { it.key.equals(key, ignoreCase = true) }
        }
    }
}

enum class ItemAmount(override val key: String) : Keyed {
    Any("Any Amount"),
    Ge("Equal or Greater Amount");

    companion object {
        fun fromKey(key: String): ItemAmount? {
            return entries.find { it.key.equals(key, ignoreCase = true) }
        }
    }
}

enum class InventoryLocation(override val key: String) : Keyed {
    Hand("Hand"),
    Armor("Armor"),
    HotBar("Hotbar"),
    Inventory("Inventory"),
    Anywhere("Anywhere");

    companion object {
        fun fromKey(key: String): InventoryLocation? {
            return entries.find { it.key.equals(key, ignoreCase = true) }
        }
    }
}

enum class FishingEnvironment(override val key: String) : Keyed {
    Water("Water"),
    Lava("Lava");

    companion object {
        fun fromKey(key: String): FishingEnvironment? {
            return entries.find { it.key.equals(key, ignoreCase = true) }
        }
    }
}

enum class PortalType(override val key: String) : Keyed {
    EndPortal("End Portal"),
    NetherPortal("Nether Portal");

    companion object {
        fun fromKey(key: String): PortalType? {
            return entries.find { it.key.equals(key, ignoreCase = true) }
        }
    }
}

enum class DamageCause(override val key: String) : Keyed {
    EntityAttack("Entity Attack"),
    Projectile("Projectile"),
    Suffocation("Suffocation"),
    Fall("Fall"),
    Lava("Lava"),
    Fire("Fire"),
    FireTick("Fire Tick"),
    Drowning("Drowning"),
    Starvation("Starvation"),
    Poison("Poison"),
    Thorns("Thorns");

    companion object {
        fun fromKey(key: String): DamageCause? {
            return entries.find { it.key.equals(key, ignoreCase = true) }
        }
    }
}