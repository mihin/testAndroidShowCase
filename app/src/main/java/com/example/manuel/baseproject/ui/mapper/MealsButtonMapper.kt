package com.example.manuel.baseproject.ui.mapper

import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.commons.utils.enums.MealsType

class MealsButtonMapper {

    companion object {
        private val VEGETABLES = MealsType.VEGETABLES
        private val PORK = MealsType.PORK
        private val BEEF = MealsType.BEEF
        private val CHICKEN = MealsType.CHICKEN
        private val ALL = MealsType.ALL
    }

    object IdToEnum : BaseMapper<Int, MealsType> {
        override fun mapFrom(type: Int?): MealsType =
                when (type) {
                    R.id.activity_meals_selector_vegetables -> VEGETABLES
                    R.id.activity_meals_selector_pork -> PORK
                    R.id.activity_meals_selector_beef -> BEEF
                    R.id.activity_meals_selector_chicken -> CHICKEN
                    else -> ALL
                }
    }
}
