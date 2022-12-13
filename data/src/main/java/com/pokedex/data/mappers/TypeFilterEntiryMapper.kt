package com.pokedex.data.mappers

import com.pokedex.data.entities.TypeFilterApiResponse
import com.pokedex.domain.entities.TypeFilterData
import javax.inject.Inject

class TypeFilterEntiryMapper @Inject constructor(): Mapper<TypeFilterData, TypeFilterApiResponse> {
    override fun mapToDomainLayer(input: TypeFilterApiResponse): TypeFilterData {
        return TypeFilterData(
            typeFilters = input.results.map { filterResults -> filterResults.name}
        )
    }

}