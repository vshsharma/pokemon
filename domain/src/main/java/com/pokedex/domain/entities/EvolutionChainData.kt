package com.pokedex.domain.entities

data class EvolutionChainData(
    var species: List<Species?> = listOf(),
)
data class Species(
    var name: String? = null,
    var url: String? = null
)
