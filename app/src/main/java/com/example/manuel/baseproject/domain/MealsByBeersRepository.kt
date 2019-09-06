package com.example.manuel.baseproject.domain

import com.example.manuel.baseproject.commons.datatype.Result
import com.example.manuel.baseproject.domain.model.BeersEntity

interface MealsByBeersRepository {

    suspend fun getAllBeers(): Result<BeersEntity>?
}