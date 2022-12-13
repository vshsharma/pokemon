package com.pokedex.data.mappers

import com.pokedex.data.entities.PokedexApiResponse
import com.pokedex.domain.entities.PokemonData
import com.pokedex.domain.entities.Results
import javax.inject.Inject

class PokemonListEntityMapper @Inject constructor() : Mapper<PokemonData, PokedexApiResponse> {

    override fun mapToDomainLayer(input: PokedexApiResponse): PokemonData {
        return PokemonData(count = input.count, next = input.next, previous = input.previous,
            results = input.results.map { results ->
                Results(
                    name = results.name,
                    url = results.url
                )
            }.toMutableList()
        )
    }
}