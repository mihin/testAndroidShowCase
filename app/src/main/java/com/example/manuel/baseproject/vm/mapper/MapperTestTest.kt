package com.example.manuel.baseproject.vm.mapper

import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.vm.model.AbvType
import com.example.manuel.baseproject.vm.model.BeerUI

object MapperTestTest : BaseMapper<List<BeerModel>, List<BeerUI>> {
    override fun mapFrom(type: List<BeerModel>?): List<BeerUI> {
        var result: MutableList<BeerUI> = mutableListOf()

        type?.let { beers ->
            beers.forEach {
                result.add(
                        BeerUI(
                                id = it.id,
                                name = it.name,
                                tagline = it.tagline,
                                image = it.image,
                                abv = it.abv,
                                abvType = MapperTestTest.getAbvType(it.abv!!)
                        )
                )
            }
        }

        return result
    }

    private fun getAbvType(abv: Double) =
            when {
                abv < 5 -> AbvType.GREEN
                abv >= 5 && abv < 8 -> AbvType.ORANGE
                else -> AbvType.RED
            }
}