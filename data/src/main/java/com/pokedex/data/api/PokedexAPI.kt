package com.pokedex.data.api

import com.pokedex.data.entities.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokedexAPI {
    @GET("pokemon")
    suspend fun getPokemonList(): Response<PokedexApiResponse>

    @GET("pokemon/{pokemonId}")
    suspend fun getPokemonDetail(@Path("pokemonId") pokemonId: String): Response<PokedexDetailApiResponse>

    @GET("pokemon-species/{pokemonId}")
    suspend fun getPokemonAdditionalDetail(@Path("pokemonId") pokemonId: String): Response<PokemonAdditionalInfo>

    @GET("type/{pokemonId}")
    suspend fun getStrengthWeakness(@Path("pokemonId") pokemonId: String): Response<StrengthWeaknessAPIResponse>

    @GET("type")
    suspend fun getTypeFilter(): Response<TypeFilterApiResponse>

    @GET("gender")
    suspend fun getGenderFilter(): Response<GenderFilterApiResponse>

    @GET("evolution-chain/{id}/")
    suspend fun getEvolutionChainData(@Path("id") id: String): Response<EvolutionChainApiResponse>
}