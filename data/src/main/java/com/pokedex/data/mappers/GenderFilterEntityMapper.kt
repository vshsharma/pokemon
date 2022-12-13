package com.pokedex.data.mappers

import com.pokedex.data.entities.GenderFilterApiResponse
import com.pokedex.domain.entities.GenderFilterData
import javax.inject.Inject

class GenderFilterEntityMapper @Inject constructor(): Mapper<GenderFilterData, GenderFilterApiResponse> {
    override fun mapToDomainLayer(input: GenderFilterApiResponse): GenderFilterData {
        return GenderFilterData(
            genderResult = input.results.map { genderResults -> genderResults.name }
        )
    }
}