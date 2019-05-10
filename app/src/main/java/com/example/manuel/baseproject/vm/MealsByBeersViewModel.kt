package com.example.manuel.baseproject.vm

import android.arch.lifecycle.MutableLiveData
import com.example.manuel.baseproject.commons.utils.dto.Result
import com.example.manuel.baseproject.commons.utils.enums.ResultType
import com.example.manuel.baseproject.commons.vm.BaseViewModel
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.vm.mapper.Mapper
import com.example.manuel.baseproject.vm.model.BeerUI
import kotlinx.coroutines.*

class MealsByBeersViewModel(private val getMealsByBeersUseCase: GetBeersUseCase) : BaseViewModel() {

    private val screenStateLiveData: MutableLiveData<Result<List<BeerUI>>> =
            MutableLiveData()

    init {
        handleBeersResultLoad()
    }

    fun getScreenStateLiveData() = screenStateLiveData

    private fun handleBeersResultLoad() {
        setScreenStateLiveDataToLoadingState()

        job = GlobalScope.launch(Dispatchers.IO) {
            getMealsByBeersUseCase.execute().let {
                screenStateLiveData.postValue(Mapper.mapFrom(it))
            }
        }
    }

    private fun setScreenStateLiveDataToLoadingState() {
        screenStateLiveData.value = Result.loading()
    }
}
