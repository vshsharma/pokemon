package com.pokedex.data.repositories

import com.pokedex.domain.common.Result
import com.pokedex.domain.entities.*

interface RemoteDataSource {
    suspend fun getPokemonList(): Result<PokemonData>
    suspend fun getPokemonDetail(pokemonId: String): Result<PokemonDetailData>
    suspend fun getPokemonAdditionlDetail(pokemonId: String): Result<PokemonAdditionalInfoData>
    suspend fun getStrengthWeakness(pokemonId: String): Result<StrengthWeaknessData>
    suspend fun getTypeFilterData(): Result<TypeFilterData>
    suspend fun getGenderFilterData(): Result<GenderFilterData>
    suspend fun getEvolutionChainData(id: String): Result<EvolutionChainData>
}