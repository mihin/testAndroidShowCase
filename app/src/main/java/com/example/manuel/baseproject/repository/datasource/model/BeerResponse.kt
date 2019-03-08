package com.example.manuel.baseproject.repository.datasource.model

import com.google.gson.annotations.SerializedName

data class BeerResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("tagline") val tagline: String,
        @SerializedName("image_url") val image: String,
        @SerializedName("abv") val abv: Double
)