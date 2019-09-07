package com.example.manuel.baseproject.di

import com.example.manuel.baseproject.datasource.MealsByBeersNetworkDataSource
import com.example.manuel.baseproject.datasource.retrofit.BeersApiService
import com.example.manuel.baseproject.domain.MealsByBeersRepository
import com.example.manuel.baseproject.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.repository.MealsByBeersRepositoryImpl
import com.example.manuel.baseproject.vm.MealsByBeersViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

@ExperimentalCoroutinesApi
object BaseProjectModule {

    val mainModule = module {
        factory { provideBeersApiService(get()) }
        factory { MealsByBeersNetworkDataSource(beersApiService = get()) }
        factory { MealsByBeersRepositoryImpl(mealsByBeersNetworkDataSource = get()) as MealsByBeersRepository }
        factory { GetBeersUseCase(mealsByBeersRepository = get()) }
        viewModel { MealsByBeersViewModel(getMealsByBeersUseCase = get()) }
    }

    private fun provideBeersApiService(retrofit: Retrofit): BeersApiService {
        return retrofit.create(BeersApiService::class.java)
    }
}