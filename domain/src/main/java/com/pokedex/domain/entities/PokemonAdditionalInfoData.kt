package com.pokedex.domain.entities

data class PokemonAdditionalInfoData(
    var eggGroups: List<String?> = arrayListOf(),
    var flavorTextEntries: String = "",
    var genderRate: Int? = null,
    var id: Int? = null,
    var name: String? = null,
    var evolutionUrl: String?= null,
)