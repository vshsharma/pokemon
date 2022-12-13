package com.pokedex.data.mappers

import com.pokedex.data.entities.PokemonAdditionalInfo
import com.pokedex.domain.entities.*
import javax.inject.Inject

class PokemonAdditionalInfoEntityMapper @Inject constructor() :
    Mapper<PokemonAdditionalInfoData, PokemonAdditionalInfo> {

    override fun mapToDomainLayer(input: PokemonAdditionalInfo): PokemonAdditionalInfoData {
        val eggs = input.eggGroups.map { eggGroups -> eggGroups.name }.toList()

        return PokemonAdditionalInfoData(
            eggGroups = eggs,
            flavorTextEntries = input.flavorTextEntries.map { flavorTextEntries -> flavorTextEntries.flavorText }
                .toMutableList()[0].toString(),
            genderRate = input.genderRate,
            id = input.id,
            name = input.name,
            evolutionUrl = input.evolutionChain?.url,
        )
    }
}