package com.pokedex.data.entities

import com.google.gson.annotations.SerializedName

data class EvolutionChainApiResponse(
    @SerializedName("baby_trigger_item") var babyTriggerItem: String? = null,
    @SerializedName("chain") var chain: Chain? = Chain(),
    @SerializedName("id") var id: Int? = null
)

data class Chain(
    @SerializedName("evolution_details") var evolutionDetails: List<String> = arrayListOf(),
    @SerializedName("evolves_to") var evolvesTo: List<EvolvesTo> = arrayListOf(),
    @SerializedName("is_baby") var isBaby: Boolean? = null,
    @SerializedName("species") var species: Species? = Species()
)

data class EvolvesTo(
    @SerializedName("evolution_details") var evolutionDetails: List<EvolutionDetails> = arrayListOf(),
    @SerializedName("evolves_to") var evolvesTo: List<EvolvesTo> = arrayListOf(),
    @SerializedName("is_baby") var isBaby: Boolean? = null,
    @SerializedName("species") var species: Species? = Species()
)

data class Species(
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null
)

data class EvolutionDetails(
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("held_item") var heldItem: String? = null,
    @SerializedName("item") var item: String? = null,
    @SerializedName("known_move") var knownMove: String? = null,
    @SerializedName("known_move_type") var knownMoveType: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("min_affection") var minAffection: String? = null,
    @SerializedName("min_beauty") var minBeauty: String? = null,
    @SerializedName("min_happiness") var minHappiness: String? = null,
    @SerializedName("min_level") var minLevel: Int? = null,
    @SerializedName("needs_overworld_rain") var needsOverworldRain: Boolean? = null,
    @SerializedName("party_species") var partySpecies: String? = null,
    @SerializedName("party_type") var partyType: String? = null,
    @SerializedName("relative_physical_stats") var relativePhysicalStats: String? = null,
    @SerializedName("time_of_day") var timeOfDay: String? = null,
    @SerializedName("trade_species") var tradeSpecies: String? = null,
    @SerializedName("trigger") var trigger: Trigger? = Trigger(),
    @SerializedName("turn_upside_down") var turnUpsideDown: Boolean? = null
)

data class Trigger(
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null
)