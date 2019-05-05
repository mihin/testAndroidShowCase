package com.example.manuel.baseproject.domain

import com.example.manuel.baseproject.commons.utils.dto.Result
import com.example.manuel.baseproject.domain.model.BeerModel

interface MealsByBeersRepository {

    suspend fun getAllBeers(): Result<List<BeerModel>>
}