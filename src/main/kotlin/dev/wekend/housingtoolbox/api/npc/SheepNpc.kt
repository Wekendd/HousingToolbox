package dev.wekend.housingtoolbox.api.npc

interface SheepNpc: Npc {
    suspend fun getAge(): SheepAge
    suspend fun setAge(newAge: SheepAge)

    suspend fun getWoolColor(): WoolColor
    suspend fun setWoolColor(newWoolColor: WoolColor)

    suspend fun getSheared(): Boolean
    suspend fun setSheared(newSheared: Boolean)

    enum class SheepAge {
        ADULT,
        BABY
    }

    enum class WoolColor {
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