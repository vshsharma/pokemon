package com.pokedex.data.entities

import com.google.gson.annotations.SerializedName

data class StrengthWeaknessAPIResponse(
    @SerializedName("damage_relations") var damageRelations: DamageRelations? = DamageRelations(),
    @SerializedName("generation") var generation: Generation? = Generation(),
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("names") var names: List<Names> = arrayListOf(),
    @SerializedName("past_damage_relations") var pastDamageRelations: List<String> = arrayListOf(),
    @SerializedName("pokemon") var pokemon: List<Pokemon> = arrayListOf()
)

data class DamageRelations(
    @SerializedName("double_damage_from") var doubleDamageFrom: List<DoubleDamageFrom> = arrayListOf(),
)

data class DoubleDamageFrom(
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null
)

data class Generation(
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null
)

data class Names(
    @SerializedName("language") var language: Language? = Language(),
    @SerializedName("name") var name: String? = null
)

data class Pokemon(
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null
)