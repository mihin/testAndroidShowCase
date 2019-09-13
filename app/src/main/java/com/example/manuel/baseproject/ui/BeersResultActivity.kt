package com.example.manuel.baseproject.ui

import android.app.Activity
import androidx.lifecycle.Observer
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.ui.adapterlist.BeersAdapter
import com.example.manuel.baseproject.vm.MealsByBeersViewModel
import com.example.manuel.baseproject.vm.model.BeerUI
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_beers_results.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class BeersResultActivity : AppCompatActivity() {

    private val viewModel: MealsByBeersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beers_results)

        observerLiveData()
    }

    private fun observerLiveData() {
        viewModel.beers.observe(this, Observer(::onBeersReceived))
        viewModel.isError.observe(this, Observer { onErrorReceived() })
        viewModel.areEmptyBeers.observe(this, Observer { onEmptyBeersReceived() })
        viewModel.isLoading.observe(this, Observer(::onLoadingStateReceived))
    }

    private fun onBeersReceived(beers: List<BeerUI>) {
        showBeers(beers)
    }

    private fun showBeers(beersUI: List<BeerUI>?) {
        beersUI?.let {
            recycler_view_beers.layoutManager = LinearLayoutManager(this)

            val beersAdapter = BeersAdapter(it.toMutableList(), this)
            recycler_view_beers.adapter = beersAdapter
            beersAdapter.updateAdapter(it.toMutableList())

            recycler_view_beers.setHasFixedSize(true)
            recycler_view_beers.layoutAnimation =
                    AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)
        }
    }

    private fun onErrorReceived() {
        val activityViewGroup = findViewById<ViewGroup>(R.id.activity_beers_view_group)
        val view: View = layoutInflater.inflate(R.layout.activity_beers_results, activityViewGroup)
        Snackbar.make(
                view,
                resources.getString(R.string.network_connection_error_message),
                Snackbar.LENGTH_LONG
        ).apply {
            setAction(R.string.network_connection_error_action) { Log.i("test", "click snackbar") }
            show()
        }
    }

    private fun onEmptyBeersReceived() {
        // do something
    }

    private fun onLoadingStateReceived(isLoading: Boolean) {
        showSpinner(isLoading)
    }

    private fun showSpinner(isLoading: Boolean) {
        main_activity_spinner.apply {
            visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}
