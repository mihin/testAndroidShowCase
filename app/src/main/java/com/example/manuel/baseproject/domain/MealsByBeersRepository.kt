package com.example.manuel.baseproject.repository

import com.example.manuel.baseproject.commons.utils.dto.ResultWrapper
import com.example.manuel.baseproject.domain.model.BeerModel

interface MealsByBeersRepository {

    fun getBeers(food: String): ResultWrapper<List<BeerModel>>
}