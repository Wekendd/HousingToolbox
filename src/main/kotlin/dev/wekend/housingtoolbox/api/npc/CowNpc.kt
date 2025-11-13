package dev.wekend.housingtoolbox.api.npc

interface CowNpc: Npc {
    suspend fun getAge(): CowAge
    suspend fun setAge(newAge: CowAge)

    enum class CowAge {
        ADULT,
        BABY
    }
}