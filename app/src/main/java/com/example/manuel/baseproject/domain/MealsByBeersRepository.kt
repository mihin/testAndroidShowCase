package com.example.manuel.baseproject.domain

import com.example.manuel.baseproject.commons.utils.dto.ResultWrapper
import com.example.manuel.baseproject.commons.utils.enums.MealsType
import com.example.manuel.baseproject.domain.model.BeerModel

interface MealsByBeersRepository {

    suspend fun getBeers(mealType: MealsType): ResultWrapper<List<BeerModel>>
}