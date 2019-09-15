package com.example.manuel.baseproject.home.di

import com.example.manuel.baseproject.home.datasource.BeersNetworkDataSource
import com.example.manuel.baseproject.home.datasource.retrofit.BeersApiService
import com.example.manuel.baseproject.home.domain.HomeRepository
import com.example.manuel.baseproject.home.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.home.repository.HomeRepositoryImpl
import com.example.manuel.baseproject.home.vm.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

@ExperimentalCoroutinesApi
object HomeModule {

    val mainModule = module {
        factory { provideBeersApiService(get()) }
        factory { BeersNetworkDataSource(beersApiService = get()) }
        factory { HomeRepositoryImpl(beersNetworkDataSource = get()) as HomeRepository }
        factory { GetBeersUseCase(homeRepository = get()) }
        viewModel { HomeViewModel(getMealsByBeersUseCase = get()) }
    }

    private fun provideBeersApiService(retrofit: Retrofit): BeersApiService {
        return retrofit.create(BeersApiService::class.java)
    }
}