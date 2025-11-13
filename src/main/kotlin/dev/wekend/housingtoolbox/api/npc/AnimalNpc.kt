package dev.wekend.housingtoolbox.api.npc

interface AnimalNpc: Npc {
    suspend fun getAge(): Age
    suspend fun setAge(newAge: Age)

    enum class Age {
        ADULT,
        BABY
    }
}