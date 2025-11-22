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

    @DisplayName("Required Group")
    data class RequiredGroup(
        @SerialName("required_group")
        val group: String = null,
        @SerialName("include_higher_groups")
        val includeHigherGroups: Boolean = false,
    ) : Condition("IN_GROUP")


    sealed class VariableRequirement protected constructor(
        val holder: VariableHolder
    ): Condition("VARIABLE_REQUIREMENT")

    @DisplayName("Variable Requirement")
    data class PlayerVariableRequirement(
        val variable: String = "Kills",
        val op: Comparison = Comparison.Eq,
        val value: StatValue = null,
    ) : VariableRequirement(VariableHolder.Player)

    @DisplayName("Variable Requirement")
    data class TeamVariableRequirement(
        val team: String? = null,
        val variable: String = "Kills",
        val op: Comparison = Comparison.Eq,
        val value: StatValue = null,
    ) : VariableRequirement(VariableHolder.Team)

    @DisplayName("Variable Requirement")
    data class GlobalVariableRequirement(
        val variable: String = "Kills",
        val op: Comparison = Comparison.Eq,
        val value: StatValue = null,
    ) : VariableRequirement(VariableHolder.Global)

    @DisplayName("Required Permission")
    data class HasPermission(
        @SerialName("required_permission")
        val permission: Permission = null,
    ) : Condition("HAS_PERMISSION")

    @DisplayName("Within Region")
    data class InRegion(
        val region: String = null,
    ) : Condition("IN_REGION")

    @DisplayName("Has Item")
    data class HasItem(
        val item: ItemStack = null,
        @SerialName("what_to_check") val whatToCheck: ItemCheck = ItemCheck.Metadata,
        @SerialName("where_to_check") val whereToCheck: InventoryLocation = InventoryLocation.Anywhere,
        @SerialName("required_amount") val amount: ItemAmount = ItemAmount.Any,
    ) : Condition("HAS_ITEM")

    @DisplayName("Doing Region")
    data object InParkour : Condition("IN_PARKOUR")

    @DisplayName("Has Potion Effect")
    data class RequiredEffect(
        val effect: PotionEffect = null,
    ) : Condition("POTION_EFFECT")

    @DisplayName("Player Sneaking")
    data object PlayerSneaking : Condition("SNEAKING")

    @DisplayName("Player Flying")
    data object PlayerFlying : Condition("FLYING")

    @DisplayName("Player Health")
    data class RequiredHealth(
        val mode: Comparison = Comparison.Eq,
        val amount: StatValue = null,
    ) : Condition("HEALTH")

    @DisplayName("Max Player Health")
    data class RequiredMaxHealth(
        val mode: Comparison = Comparison.Eq,
        val amount: StatValue = null,
    ) : Condition("MAX_HEALTH")

    @DisplayName("Player Hunger")
    data class RequiredHungerLevel(
        val mode: Comparison = Comparison.Eq,
        val amount: StatValue = null,
    ) : Condition("HUNGER_LEVEL")

    @DisplayName("Required Gamemode")
    data class RequiredGameMode(
        @SerialName("required_gamemode")
        val gameMode: GameMode = null
    ) : Condition("GAMEMODE")

    @DisplayName("Placeholder Number Requirement")
    data class RequiredPlaceholderNumber(
        val placeholder: String = null,
        val mode: Comparison = Comparison.Eq,
        val amount: StatValue = null,
    ) : Condition("PLACEHOLDER_NUMBER")

    @DisplayName("Required Team")
    data class RequiredTeam(
        @SerialName("required_team")
        val team: String = null,
    ) : Condition("IN_TEAM")

    @DisplayName("Pvp Enabled")
    data object PvpEnabled : Condition("PVP_ENABLED")

    @DisplayName("Fishing Environment")
    data class FishingEnvironment(
        val environment: dev.wekend.housingtoolbox.feature.data.FishingEnvironment = dev.wekend.housingtoolbox.feature.data.FishingEnvironment.Water
    ) : Condition("FISHING_ENVIRONMENT")

    @DisplayName("Portal Type")
    data class PortalType(
        @SerialName("portal_type")
        val type: dev.wekend.housingtoolbox.feature.data.PortalType = dev.wekend.housingtoolbox.feature.data.PortalType.NetherPortal
    ) : Condition("PORTAL_TYPE")

    @DisplayName("Damage Cause")
    data class DamageCause(
        val cause: dev.wekend.housingtoolbox.feature.data.DamageCause = null
    ) : Condition("DAMAGE_CAUSE")

    @DisplayName("Damage Amount")
    data class RequiredDamageAmount(
        val mode: Comparison = Comparison.Eq,
        val amount: StatValue = null,
    ) : Condition("DAMAGE_AMOUNT")

    @DisplayName("Block Type")
    data class BlockType(
        val item: ItemStack = null,
        @SerialName("match_type_only")
        val matchTypeOnly: Boolean = false,
    ) : Condition("BLOCK_TYPE")

    @DisplayName("Is Item")
    data class IsItem(
        val item: ItemStack = null,
        @SerialName("what_to_check") val whatToCheck: ItemCheck = ItemCheck.Metadata,
        @SerialName("where_to_check") val whereToCheck: InventoryLocation = InventoryLocation.Anywhere,
        @SerialName("required_amount") val amount: ItemAmount = ItemAmount.Any,
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