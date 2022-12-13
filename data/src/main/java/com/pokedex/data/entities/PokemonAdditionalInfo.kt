package com.pokedex.data.entities

import com.google.gson.annotations.SerializedName

data class PokemonAdditionalInfo(
    @SerializedName("egg_groups") var eggGroups: ArrayList<EggGroups> = arrayListOf(),
    @SerializedName("evolution_chain") var evolutionChain: EvolutionChain? = EvolutionChain(),
    @SerializedName("flavor_text_entries") var flavorTextEntries: ArrayList<FlavorTextEntries> = arrayListOf(),
    @SerializedName("gender_rate") var genderRate: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
)

data class EggGroups(
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null
)

data class EvolutionChain(
    @SerializedName("url") var url: String? = null
)

data class FlavorTextEntries(
    @SerializedName("flavor_text") var flavorText: String? = null,
    @SerializedName("language") var language: Language? = Language(),
)

data class Language(
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null
)