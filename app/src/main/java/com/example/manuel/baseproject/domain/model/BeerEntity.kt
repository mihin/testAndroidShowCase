package com.example.manuel.baseproject.domain.model

import com.example.manuel.baseproject.vm.model.AbvType

class BeerEntity(
        val id: Int,
        val name: String,
        val tagline: String,
        val image: String,
        val abv: Double
) {
    fun getAbvType(abv: Double): AbvType {
        return when {
            abv < 5 -> AbvType.GREEN
            abv >= 5 && abv < 8 -> AbvType.ORANGE
            else -> AbvType.RED
        }
    }
}