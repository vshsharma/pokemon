package com.pokedex.pokemon

import com.google.gson.Gson
import com.pokedex.domain.common.Result
import com.pokedex.domain.entities.*
import com.pokedex.pokemon.core.MockStringResponses
import com.pokedex.pokemon.core.MockStringResponses.additionalInfo
import com.pokedex.pokemon.core.MockStringResponses.evolutionData
import com.pokedex.pokemon.core.MockStringResponses.pokemonDetail
import com.pokedex.pokemon.core.MockStringResponses.pokemonList
import com.pokedex.pokemon.core.MockStringResponses.strengthWeakness

object MockResponse {

    const val error_message = "something went wrong"

    fun getSuccessResult(): Result<PokemonData> {
        return Result.Success(
             Gson().fromJson(pokemonList, PokemonData::class.java)
        )
    }

    fun getDetailSuccessResult(): Result<PokemonDetailData> {
        return Result.Success(
            Gson().fromJson(pokemonDetail, PokemonDetailData::class.java)
        )
    }

    fun getPokemonAdditionalSuccessResult(): Result<PokemonAdditionalInfoData> {
        return Result.Success(
            Gson().fromJson(additionalInfo, PokemonAdditionalInfoData::class.java)
        )
    }

    fun getStrengthWeaknessResult(): Result<StrengthWeaknessData> {
        return Result.Success(
            Gson().fromJson(strengthWeakness, StrengthWeaknessData::class.java)
        )
    }

    fun getEvolutionData(): Result<EvolutionChainData> {
        return Result.Success(
            Gson().fromJson(evolutionData, EvolutionChainData::class.java)
        )
    }

    fun getTypeFilterData(): Result<TypeFilterData> {
        return Result.Success(
            Gson().fromJson(MockStringResponses.types, TypeFilterData::class.java)
        )
    }
    fun getGenderFilterData(): Result<GenderFilterData> {
        return Result.Success(
            Gson().fromJson(MockStringResponses.gender, GenderFilterData::class.java)
        )
    }
}