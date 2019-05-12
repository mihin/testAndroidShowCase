package com.example.manuel.baseproject.ui.adapterlist

import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.recyclerview.widget.DiffUtil
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.vm.model.BeerUI
import kotlinx.android.synthetic.main.item_list_beer.view.*

class BeersAdapter(private var beers: MutableList<BeerUI>, private val context: Context) :
        RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.item_list_beer, parent, false)
            )


    override fun getItemCount(): Int = beers.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            val abv = beers[position].abv.toString()
            val formattedAbv: String = context.resources.getString(R.string.abv, abv)

            beerAbvTextView.text = formattedAbv
            beerNameTextView.text = beers[position].name
            beerTaglineTextView.text = beers[position].tagline

            Glide.with(context)
                    .load(beers[position].image)
                    .placeholder(R.drawable.ic_close_black)
                    .override(200, 300)
                    .into(beerImageTextView)

            setBackgroundAbv(position, this)
        }
    }

    private fun setBackgroundAbv(position: Int, viewHolder: ViewHolder) {
        val abvBackground = viewHolder.beerAbvTextView.background as GradientDrawable

        abvBackground.apply {
            val abv = beers[position].abv

            abv?.let {
                if (abv < 5.0) setColor(context.resources.getColor(R.color.green))
                if (abv >= 5.0 && abv < 8.0) setColor(context.resources.getColor(R.color.orange))
                if (abv >= 8.0) setColor(context.resources.getColor(R.color.red))
            }
        }
    }

    fun updateAdapter(updatedList: List<BeerUI>) {
        val result = DiffUtil.calculateDiff(BeersDiffCallback(this.beers, updatedList))

        this.beers = updatedList.toMutableList()
        result.dispatchUpdatesTo(this)
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val beerAbvTextView: AppCompatTextView = view.item_list_beer_abv
    val beerNameTextView: AppCompatTextView = view.item_list_beer_name
    val beerTaglineTextView: AppCompatTextView = view.item_list_beer_tagline
    val beerImageTextView: AppCompatImageView = view.item_list_beer_image
}
