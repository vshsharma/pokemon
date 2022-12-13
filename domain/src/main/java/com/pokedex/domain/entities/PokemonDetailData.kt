package com.pokedex.domain.entities

data class PokemonDetailData(
    var abilities: List<Abilities> = arrayListOf(),
    var height: Int? = null,
    var id: Int? = null,
    var isDefault: Boolean? = null,
    var name: String? = null,
    var pastTypes: ArrayList<String> = arrayListOf(),
    var sprites: Sprites? = Sprites(),
    var stats: List<Stats> = arrayListOf(),
    var types: List<Types> = arrayListOf(),
    var weight: Int? = null
)

data class Ability(
    var name: String? = null,
    var url: String? = null
)

data class Abilities(
    var ability: Ability? = Ability(),
    var isHidden: Boolean? = null,
    var slot: Int? = null
)

data class Sprites(
    var backDefault: String? = null,
    var backFemale: String? = null,
    var backShiny: String? = null,
    var backShinyFemale: String? = null,
    var frontDefault: String? = null,
    var frontFemale: String? = null,
    var frontShiny: String? = null,
    var frontShinyFemale: String? = null,
)

data class Stats(
    var baseStat: Int? = null,
    var effort: Int? = null,
    var stat: Stat? = Stat()
)

data class Stat(
    var name: String? = null,
    var url: String? = null
)

data class Type(
    var name: String? = null,
    var url: String? = null
)

data class Types(
    var slot: Int? = null,
    var type: Type? = Type()
)

