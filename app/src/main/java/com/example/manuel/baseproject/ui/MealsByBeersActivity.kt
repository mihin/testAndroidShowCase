package com.example.manuel.baseproject.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.commons.ui.BaseActivity
import com.example.manuel.baseproject.commons.utils.dto.ResultWrapper
import com.example.manuel.baseproject.commons.utils.enums.ResultType
import com.example.manuel.baseproject.di.KodeinContainers
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.ui.adapterlist.BeersAdapter
import com.example.manuel.baseproject.vm.MainActivityViewModel
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.newInstance
import kotlinx.android.synthetic.main.activity_main.*

class MealsByBeersActivity : BaseActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        initObservers()
        //initSearchButtonListener()
        onSearchButtonPress()
    }

    private fun initViewModel() {
        viewModel = KodeinContainers.diBaseProject.newInstance {
            MainActivityViewModel(instance())
        }
    }

    private fun initObservers() {
        viewModel.getScreenStateLivedata().observe(this, Observer(::onResultWrapperReceived))
    }

    private fun onResultWrapperReceived(result: ResultWrapper<List<BeerModel>>?) {
        when (result?.resultType) { //why not create a sealed class for this
            ResultType.LOADING -> showSpinner(true)
            ResultType.ERROR -> showError()
            ResultType.EMPTY_DATA -> showEmptyDataResult(result.data?.size)
            ResultType.SUCCESS -> showBeers(result.data)// do nothing
        }
    }

    private fun showError() {
        showSpinner(false)
    }

    private fun showSpinner(isViewVisible: Boolean) {
        main_activity_spinner.apply {
            visibility = if (isViewVisible) View.VISIBLE else View.GONE
        }
    }

    private fun showEmptyDataResult(beersSize: Int?) {
        // Show the message
    }

    private fun showBeers(beersModel: List<BeerModel>?) {
        showSpinner(false)
        beersModel?.let {
            recycler_view_beers.layoutManager = LinearLayoutManager(this)

            val beersAdapter = BeersAdapter(it.toMutableList(), this)
            recycler_view_beers.adapter = beersAdapter
            beersAdapter.updateAdapter(it.toMutableList())

            recycler_view_beers.setHasFixedSize(true)

            beersModel.forEach {
                Log.i("test", "id beer = ${it.id}")
            }
        }
    }

    private fun initSearchButtonListener() {
        main_activity_search_button.setOnClickListener { onSearchButtonPress() }
    }

    private fun onSearchButtonPress() {
        viewModel.onSearchButtonPress("chicken")
    }
}
