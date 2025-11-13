package dev.wekend.housingtoolbox.api.npc

interface PigNpc: Npc {
    suspend fun getAge(): PigAge
    suspend fun setAge(newAge: PigAge)

    suspend fun getSaddled(): Boolean
    suspend fun setSaddled(newSaddled: Boolean)

    enum class PigAge {
        ADULT,
        BABY
    }
}