package com.pokedex.domain.entities

data class StrengthWeaknessData(
    var damageRelations: DamageRelations? = DamageRelations(),
    var generation: Generation? = Generation(),
    var id: Int? = null,
    var name: String? = null,
    var names: List<Names> = arrayListOf(),
    var pastDamageRelations: List<String> = arrayListOf(),
    var pokemon: List<Pokemon> = arrayListOf()

)

data class DamageRelations(
    var doubleDamageFrom: List<String?> = arrayListOf(),
)

data class Generation(
    var name: String? = null,
    var url: String? = null
)

data class Names(
    var name: String? = null
)

data class Pokemon(
    var name: String? = null,
    var url: String? = null
)
