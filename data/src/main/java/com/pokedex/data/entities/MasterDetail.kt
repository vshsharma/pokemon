package com.pokedex.data.entities

import com.pokedex.domain.entities.PokemonAdditionalInfoData
import com.pokedex.domain.entities.PokemonData
import com.pokedex.domain.entities.PokemonDetailData
import com.pokedex.domain.entities.StrengthWeaknessData

data class MasterDetail(
    var pokemonDetailData: PokemonDetailData? = PokemonDetailData(),
    var pokemonAdditionalInfoData: PokemonAdditionalInfoData = PokemonAdditionalInfoData(),
    var strengthWeaknessData: StrengthWeaknessData? = StrengthWeaknessData(),
)
