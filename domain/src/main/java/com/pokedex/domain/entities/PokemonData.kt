package com.pokedex.domain.entities

data class PokemonData(
    var count: Int? = null,
    var next: String? = null,
    var previous: String? = null,
    var results: List<Results> = arrayListOf()
)

data class Results(
    var name: String? = null,
    var url: String? = null

)
