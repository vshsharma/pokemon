package com.pokedex.data.entities

import com.google.gson.annotations.SerializedName

data class GenderFilterApiResponse(
    @SerializedName("count") var count: Int? = null,
    @SerializedName("next") var next: String? = null,
    @SerializedName("previous") var previous: String? = null,
    @SerializedName("results") var results: ArrayList<GenderResults> = arrayListOf()
)

class GenderResults (
    @SerializedName("name" ) var name : String? = null,
    @SerializedName("url"  ) var url  : String? = null
)
