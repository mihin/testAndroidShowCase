package com.example.manuel.baseproject.home.ui

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.home.ui.adapterlist.BeersAdapter
import com.example.manuel.baseproject.home.ui.mapper.BeerUIMapper
import com.example.manuel.baseproject.home.ui.adapterlist.model.BeerAdapterModel
import com.example.manuel.baseproject.home.ui.adapterlist.model.BeersAdapterModel
import com.example.manuel.baseproject.home.vm.HomeViewModel
import com.example.manuel.baseproject.home.vm.model.BeerUI
import kotlinx.android.synthetic.main.activity_beers_results.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val BUNDLE_KEY_BEERS_ADAPTER_MODEL = "BUNDLE_KEY_BEERS_ADAPTER_MODEL"
    }

    private val viewModel: HomeViewModel by viewModel()

    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beers_results)

        bindViews()

        if (savedInstanceState == null) {
            observerLiveData()
        } else {
            startBundleConfiguration(savedInstanceState)
        }
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.recycler_view_beers)
    }

    /**
     *  If is necessary, this method recover the UI state when the device has a memory error,
     *  so the UI will be restored.
     *
     *  The first time there is not data in the adapter and maybe the device has a network error,
     *  also prevents it.
     * */
    private fun startBundleConfiguration(savedInstanceState: Bundle) {
        if (savedInstanceState.containsKey(BUNDLE_KEY_BEERS_ADAPTER_MODEL)) {
            restoreUIStateAfterMemoryError(savedInstanceState)
        } else {
            observerLiveData()
        }
    }

    private fun restoreUIStateAfterMemoryError(savedInstanceState: Bundle) {
        val beersAdapterModel = savedInstanceState.getParcelable(
                BUNDLE_KEY_BEERS_ADAPTER_MODEL
        ) as BeersAdapterModel

        populateRecyclerView(beersAdapterModel.beers)
        recyclerView?.scrollToPosition(beersAdapterModel.positionLastVisibleItem)
    }

    private fun onBeersReceived(beers: List<BeerUI>) {
        showBeers(beers)
    }

    private fun showBeers(beersUI: List<BeerUI>?) {
        beersUI?.let {
            populateRecyclerView(BeerUIMapper.map(it))
        }
    }

    private fun populateRecyclerView(beersAdapterModel: List<BeerAdapterModel>) {
        recyclerView?.layoutManager = LinearLayoutManager(this)

        val beersAdapter = BeersAdapter(beersAdapterModel, this)
        recyclerView?.adapter = beersAdapter
        beersAdapter.updateAdapter(beersAdapterModel)

        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutAnimation =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)
    }

    private fun observerLiveData() {
        viewModel.beers.observe(this, Observer(::onBeersReceived))
        viewModel.isError.observe(this, Observer { onErrorReceived() })
        viewModel.areEmptyBeers.observe(this, Observer { onEmptyBeersReceived() })
        viewModel.isLoading.observe(this, Observer(::onLoadingStateReceived))
    }

    private fun onErrorReceived() {
        AlertDialog.Builder(this)
                .setTitle(R.string.network_connection_error_title)
                .setCancelable(false)
                .setNegativeButton(R.string.network_connection_error_cancel) { _, _ ->
                    finish()
                }
                .setPositiveButton(R.string.network_connection_error_action) { _, _ ->
                    viewModel.handleBeersLoad()
                }.show()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        saveViewStateWhenActivityIsDestroyed(outState)
    }

    private fun saveViewStateWhenActivityIsDestroyed(outState: Bundle) {
        recyclerView?.let {
            it.layoutManager?.let { layoutManager ->
                val lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                val beers = (it.adapter as BeersAdapter).getBeers()

                outState.putParcelable(
                        BUNDLE_KEY_BEERS_ADAPTER_MODEL,
                        BeersAdapterModel(lastVisibleItemPosition, beers)
                )
            }
        }
    }
}
