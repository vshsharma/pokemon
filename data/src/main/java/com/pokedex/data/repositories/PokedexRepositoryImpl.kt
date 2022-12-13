package com.pokedex.data.repositories

import com.pokedex.domain.repositories.PokedexRepository
import com.pokedex.domain.common.Result
import com.pokedex.domain.entities.*
import javax.inject.Inject

class PokedexRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): PokedexRepository  {
    override suspend fun getPokemonList(): Result<PokemonData> {
        return  remoteDataSource.getPokemonList()
    }

    override suspend fun getPokemonDetail(pokemonId: String): Result<PokemonDetailData> {
        return remoteDataSource.getPokemonDetail(pokemonId)
    }

    override suspend fun getPokemonAdditionalDetail(pokemonId: String): Result<PokemonAdditionalInfoData> {
        return remoteDataSource.getPokemonAdditionlDetail(pokemonId)
    }

    override suspend fun getStrengthAndWeakness(pokemonId: String): Result<StrengthWeaknessData> {
        return remoteDataSource.getStrengthWeakness(pokemonId)
    }

    override suspend fun getTypeFilter(): Result<TypeFilterData> {
        return remoteDataSource.getTypeFilterData()
    }

    override suspend fun getGenderFilter(): Result<GenderFilterData> {
        return remoteDataSource.getGenderFilterData()
    }

    override suspend fun getEvolutionChainData(id: String): Result<EvolutionChainData> {
        return remoteDataSource.getEvolutionChainData(id)
    }

}