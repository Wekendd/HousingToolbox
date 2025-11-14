package dev.wekend.housingtoolbox.api.npc

interface RabbitNpc: AnimalNpc {
    suspend fun getRabbitType(): RabbitType
    suspend fun setRabbitType(newRabbitType: RabbitType)

    enum class RabbitType {
        BLACK,
        GOLD,
        BROWN,
        BLACK_AND_WHITE,
        SALT_AND_PEPPER,
        WHITE
    }
}