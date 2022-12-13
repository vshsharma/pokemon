package com.pokedex.data.mappers

import com.pokedex.data.entities.EvolutionChainApiResponse
import com.pokedex.data.entities.EvolvesTo
import com.pokedex.domain.entities.EvolutionChainData
import com.pokedex.domain.entities.Species
import javax.inject.Inject

class EvolutionChainEntityMapper @Inject constructor() :
    Mapper<EvolutionChainData, EvolutionChainApiResponse> {

    override fun mapToDomainLayer(input: EvolutionChainApiResponse): EvolutionChainData {
        val resultSpecies: MutableList<Species> = mutableListOf()
        resultSpecies.add(Species(input.chain?.species?.name, input.chain?.species?.url))
        getSpecies(input.chain?.evolvesTo, resultSpecies)
        return EvolutionChainData(resultSpecies)
    }

    private fun getSpecies(evolvesTo: List<EvolvesTo>?, resultSpecies: MutableList<Species>) {
        if (evolvesTo != null) {
            for (evolveObj in evolvesTo) {
                resultSpecies.add(
                    Species(
                        name = evolveObj.species?.name,
                        url = evolveObj.species?.url
                    )
                )
                getSpecies(evolveObj.evolvesTo, resultSpecies)
            }
        }
    }
}