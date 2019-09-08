package com.example.manuel.baseproject.domain

import com.example.manuel.baseproject.domain.usecase.GetBeersUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import com.example.manuel.baseproject.commons.datatype.Result
import com.example.manuel.baseproject.domain.model.BeersEntity
import com.example.manuel.baseproject.domain.model.BusinessErrorType
import com.example.manuel.baseproject.domain.utils.DomainBeersGenerator
import com.example.manuel.baseproject.repository.constants.NetworkError
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Test

class GetBeersUseCaseTest {

    private val mockMealsByBeersRepository: MealsByBeersRepository = mock()
    private val getBeersUseCase = GetBeersUseCase(mockMealsByBeersRepository)

    @Test
    fun <T> verifyBusinessErrorWhenRepoMockReturnNetworkError() {
        runBlocking {
            given(mockMealsByBeersRepository.getAllBeers())
                    .willReturn(Result.error(NetworkError.API_ERROR))

            val expectedResult = Result.error<T>(BusinessErrorType.NETWORK_ERROR).error
            val realResult = getBeersUseCase.execute().error

            Assert.assertEquals(expectedResult, realResult)
        }
    }

    @Test
    fun verifyResultWhenRepoMockReturnSuccessState() {
        runBlocking {
            val result = Result.success(BeersEntity(listOf()))
            given(mockMealsByBeersRepository.getAllBeers()).willReturn(result)

            val expectedResult = Result.success(BeersEntity(listOf()))
            val realResult = getBeersUseCase.execute()

            Assert.assertEquals(expectedResult, realResult)
        }
    }

    @Test
    fun verifySortedAbvWhenRepoMockReturnUnsortedList() {
        runBlocking {
            val result = Result.success(DomainBeersGenerator.getUnsortedBeers())
            given(mockMealsByBeersRepository.getAllBeers()).willReturn(result)

            val expectedResultBeers = Result.success(DomainBeersGenerator.getSortedBeers()).data
            val realResultBeers = getBeersUseCase.execute().data

            realResultBeers?.beers?.forEachIndexed { index, currentRealResult ->
                val currentExpectedResult = expectedResultBeers?.beers?.get(index)?.abv
                val realResult = currentRealResult.abv

                Assert.assertEquals(realResult, currentExpectedResult)
            }
        }
    }

    @Test
    fun verifyUseCaseCallRepository() {
        runBlocking {
            given(mockMealsByBeersRepository.getAllBeers())
                    .willReturn(Result.success(BeersEntity(listOf())))

            getBeersUseCase.execute()

            verify(mockMealsByBeersRepository, times(1)).getAllBeers()
        }
    }
}