package com.pokedex.data.entities

import com.google.gson.annotations.SerializedName

data class PokedexDetailApiResponse(
    @SerializedName("abilities") var abilities: ArrayList<Abilities> = arrayListOf(),
    @SerializedName("height") var height: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("is_default") var isDefault: Boolean? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("past_types") var pastTypes: ArrayList<String> = arrayListOf(),
    @SerializedName("sprites") var sprites: Sprites? = Sprites(),
    @SerializedName("stats") var stats: ArrayList<Stats> = arrayListOf(),
    @SerializedName("types") var types: ArrayList<Types> = arrayListOf(),
    @SerializedName("weight") var weight: Int? = null
)

data class Ability(
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null
)

data class Abilities(
    @SerializedName("ability") var ability: Ability? = Ability(),
    @SerializedName("is_hidden") var isHidden: Boolean? = null,
    @SerializedName("slot") var slot: Int? = null
)

data class Sprites(
    @SerializedName("back_default") var backDefault: String? = null,
    @SerializedName("back_female") var backFemale: String? = null,
    @SerializedName("back_shiny") var backShiny: String? = null,
    @SerializedName("back_shiny_female") var backShinyFemale: String? = null,
    @SerializedName("front_default") var frontDefault: String? = null,
    @SerializedName("front_female") var frontFemale: String? = null,
    @SerializedName("front_shiny") var frontShiny: String? = null,
    @SerializedName("front_shiny_female") var frontShinyFemale: String? = null,
)

data class Stats(
    @SerializedName("base_stat") var baseStat: Int? = null,
    @SerializedName("effort") var effort: Int? = null,
    @SerializedName("stat") var stat: Stat? = Stat()
)

data class Stat(
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null
)

data class Type(
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null
)

data class Types (
    @SerializedName("slot" ) var slot : Int?  = null,
    @SerializedName("type" ) var type : Type? = Type()
)
