package com.pokedex.data.entities

import com.google.gson.annotations.SerializedName

data class TypeFilterApiResponse(
    @SerializedName("count") var count: Int? = null,
    @SerializedName("next") var next: String? = null,
    @SerializedName("previous") var previous: String? = null,
    @SerializedName("results") var results: ArrayList<FilterResults> = arrayListOf()
)

data class FilterResults (
    @SerializedName("name" ) var name : String? = null,
    @SerializedName("url"  ) var url  : String? = null
)
