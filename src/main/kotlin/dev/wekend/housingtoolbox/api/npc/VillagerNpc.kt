package dev.wekend.housingtoolbox.api.npc

interface VillagerNpc: AgedNpc {
    suspend fun getProfession(): VillagerProfession
    suspend fun setProfession(newProfession: VillagerProfession)

    enum class VillagerProfession {
        FARMER,
        LIBRARIAN,
        PRIEST,
        BLACKSMITH,
        BUTCHER,
    }
}