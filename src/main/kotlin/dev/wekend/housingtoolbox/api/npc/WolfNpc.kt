package dev.wekend.housingtoolbox.api.npc

interface WolfNpc: AgedNpc {
    suspend fun getColarColor(): ColarColor
    suspend fun setColarColor(newColarColor: ColarColor)

    suspend fun getSitting(): Boolean
    suspend fun setSitting(newSitting: Boolean)

    enum class ColarColor {
        WHITE,
        ORANGE,
        MAGENTA,
        LIGHT_BLUE,
        YELLOW,
        LIME,
        PINK,
        GRAY,
        SILVER,
        CYAN,
        PURPLE,
        BLUE,
        BROWN,
        GREEN,
        RED,
        BLACK
    }
}