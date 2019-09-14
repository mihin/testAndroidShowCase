package com.example.manuel.baseproject.vm.mapper

import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.vm.model.AbvColorType
import com.example.manuel.baseproject.domain.model.AbvRangeType
import com.example.manuel.baseproject.domain.model.BeerEntity
import com.example.manuel.baseproject.vm.model.BeerUI

class ViewModelMapper {

    object EntityToUI : BaseMapper<List<BeerEntity>, List<BeerUI>> {
        override fun map(type: List<BeerEntity>?): List<BeerUI> {
            return type?.map {
                BeerUI(
                        id = it.id,
                        name = it.name,
                        tagline = it.tagline,
                        image = it.image,
                        abv = it.abv,
                        abvColorType = mapAbvType(it.getAbvRange(it.abv))
                )
            } ?: listOf()
        }
    }

    companion object {
        private fun mapAbvType(abvRangeType: AbvRangeType): AbvColorType {
            return when (abvRangeType) {
                AbvRangeType.LOW -> AbvColorType.GREEN
                AbvRangeType.NORMAL -> AbvColorType.ORANGE
                AbvRangeType.HIGH -> AbvColorType.RED
            }
        }
    }
}
