package com.example.manuel.baseproject.domain

import com.example.manuel.baseproject.home.domain.usecase.GetBeersUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import com.example.manuel.baseproject.home.commons.datatype.Result
import com.example.manuel.baseproject.home.commons.exceptions.NetworkConnectionException
import com.example.manuel.baseproject.home.domain.model.BeersEntity
import com.example.manuel.baseproject.domain.utils.DomainBeersGenerator
import com.example.manuel.baseproject.home.domain.HomeRepository
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Test
import java.lang.Exception

class GetBeersUseCaseTest {

    private val mockHomeRepository: HomeRepository = mock()
    private val getBeersUseCase = GetBeersUseCase(mockHomeRepository)

    @Test
    fun verifyBusinessErrorWhenRepoMockReturnNetworkError() {
        runBlocking {
            given(mockHomeRepository.getAllBeers())
                    .willReturn(Result.error(NetworkConnectionException()))

            val expectedResult = Result.error<Exception>(NetworkConnectionException()).error
            val realResult = getBeersUseCase.execute().error

            Assert.assertEquals(expectedResult is NetworkConnectionException, realResult is NetworkConnectionException)
        }
    }

    @Test
    fun verifyResultWhenRepoMockReturnSuccessState() {
        runBlocking {
            val result = Result.success(BeersEntity(listOf()))
            given(mockHomeRepository.getAllBeers()).willReturn(result)

            val expectedResult = Result.success(BeersEntity(listOf()))
            val realResult = getBeersUseCase.execute()

            Assert.assertEquals(expectedResult, realResult)
        }
    }

    @Test
    fun verifySortedAbvWhenRepoMockReturnUnsortedList() {
        runBlocking {
            val result = Result.success(DomainBeersGenerator.getUnsortedBeers())
            given(mockHomeRepository.getAllBeers()).willReturn(result)

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
            given(mockHomeRepository.getAllBeers())
                    .willReturn(Result.success(BeersEntity(listOf())))

            getBeersUseCase.execute()

            verify(mockHomeRepository, times(1)).getAllBeers()
        }
    }
}
