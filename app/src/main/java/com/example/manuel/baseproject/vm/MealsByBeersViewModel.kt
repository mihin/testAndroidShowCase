package com.example.manuel.baseproject.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manuel.baseproject.commons.utils.dto.Result
import com.example.manuel.baseproject.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.vm.mapper.Mapper
import com.example.manuel.baseproject.vm.model.BeerUI
import kotlinx.coroutines.*

class MealsByBeersViewModel(private val getMealsByBeersUseCase: GetBeersUseCase) : ViewModel() {

    private val screenStateLiveData: MutableLiveData<Result<List<BeerUI>>> =
            MutableLiveData()

    val getScreenStateLiveData: LiveData<Result<List<BeerUI>>>
        get() = screenStateLiveData

    init {
        handleBeersResultLoad()
    }

    private fun handleBeersResultLoad() {
        viewModelScope.launch {
            setScreenStateLiveDataToLoadingState()
            getMealsByBeersUseCase.execute().let {
                screenStateLiveData.postValue(Mapper.mapFrom(it))
            }
        }
    }

    private fun setScreenStateLiveDataToLoadingState() {
        screenStateLiveData.value = Result.loading()
    }
}
