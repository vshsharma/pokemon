package com.pokedex.domain.usecases

import com.pokedex.domain.repositories.PokedexRepository
import javax.inject.Inject

class PokedexUseCase @Inject constructor(private val pokedexRepository: PokedexRepository) {
    suspend operator fun invoke() = pokedexRepository.getPokemonList()
    suspend fun invokePokemonDetail(pokemonId: String)= pokedexRepository.getPokemonDetail(pokemonId)
    suspend fun invokePokemonAdditionalDetail(pokemonId: String)= pokedexRepository.getPokemonAdditionalDetail(pokemonId)
    suspend fun invokeStrengthWeaknessDetail(pokemonId: String)= pokedexRepository.getStrengthAndWeakness(pokemonId)
    suspend fun invokeTypeFilterData()= pokedexRepository.getTypeFilter()
    suspend fun invokeGenderFilterData()= pokedexRepository.getGenderFilter()
    suspend fun invokeEvolutionData(id: String)= pokedexRepository.getEvolutionChainData(id)
}