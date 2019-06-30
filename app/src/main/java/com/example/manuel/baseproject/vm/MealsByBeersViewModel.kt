package com.example.manuel.baseproject.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manuel.baseproject.commons.utils.dto.Result
import com.example.manuel.baseproject.commons.utils.enums.ResultType
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.vm.mapper.Mapper
import com.example.manuel.baseproject.vm.model.BeerUI
import kotlinx.coroutines.*

class MealsByBeersViewModel(private val getMealsByBeersUseCase: GetBeersUseCase) : ViewModel() {

    private val beersLiveData: MutableLiveData<List<BeerUI>> = MutableLiveData()
    private val areEmptyBeersLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val beers: MutableLiveData<List<BeerUI>>
        get() = beersLiveData

    val areEmptyBeers: MutableLiveData<Boolean>
        get() = areEmptyBeersLiveData

    val isError: MutableLiveData<Boolean>
        get() = isErrorLiveData

    val isLoading: MutableLiveData<Boolean>
        get() = isLoadingLiveData

    init {
        handleBeersLoad()
    }

    private fun handleBeersLoad() {
        viewModelScope.launch {
            isLoadingLiveData(true)
            updateAppropriateLiveData(
                    getMealsByBeersUseCase.execute()
            )
        }
    }

    private fun updateAppropriateLiveData(result: Result<List<BeerModel>>) {
        if (isResultSuccess(result)) {
            onResultSuccess(result.data)
        } else {
            onResultError()
        }

        isLoadingLiveData(false)
    }

    private fun isResultSuccess(result: Result<List<BeerModel>>) =
            result.resultType == ResultType.SUCCESS

    private fun onResultSuccess(beersModel: List<BeerModel>?) {
        val beers = Mapper.mapFrom(beersModel)

        if (beers.isEmpty()) {
            areEmptyBeersLiveData.postValue(true)
        } else {
            beersLiveData.postValue(beers)
        }
    }

    private fun onResultError() {
        isErrorLiveData.postValue(true)
    }

    private fun isLoadingLiveData(isLoading: Boolean) {
        this.isLoadingLiveData.postValue(isLoading)
    }
}
