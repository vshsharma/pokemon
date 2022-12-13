package com.pokedex.domain

import com.pokedex.domain.common.Result
import com.pokedex.domain.entities.PokemonAdditionalInfoData
import com.pokedex.domain.entities.PokemonData
import com.pokedex.domain.entities.PokemonDetailData

object MockResponse {

    const val error_message = "something went wrong"

    fun getSuccessResult(): Result<PokemonData> {
        return Result.Success(
            PokemonData(
                count = 122,
                next = "next",
                previous = "previous",

                )
        )
    }

    fun getDetailSuccessResult(): Result<PokemonDetailData> {
        return Result.Success(
            PokemonDetailData(
                height = 344
            )
        )
    }

    fun getPokemonAdditionalSuccessResult(): Result<PokemonAdditionalInfoData> {
        return Result.Success(
            PokemonAdditionalInfoData(flavorTextEntries = "this is pokedex")
        )
    }
}