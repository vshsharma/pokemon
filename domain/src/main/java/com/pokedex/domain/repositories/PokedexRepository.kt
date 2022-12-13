package com.pokedex.domain.repositories

import com.pokedex.domain.common.Result
import com.pokedex.domain.entities.*

interface PokedexRepository {
    suspend fun getPokemonList(): Result<PokemonData>
    suspend fun getPokemonDetail(pokemonId: String): Result<PokemonDetailData>
    suspend fun getPokemonAdditionalDetail(pokemonId: String): Result<PokemonAdditionalInfoData>
    suspend fun getStrengthAndWeakness(pokemonId: String): Result<StrengthWeaknessData>
    suspend fun getTypeFilter(): Result<TypeFilterData>
    suspend fun getGenderFilter(): Result<GenderFilterData>
    suspend fun getEvolutionChainData(id: String): Result<EvolutionChainData>
}