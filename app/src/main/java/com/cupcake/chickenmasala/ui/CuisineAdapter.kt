package com.cupcake.chickenmasala.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Cuisine
import com.cupcake.chickenmasala.databinding.ItemCardCuisineBinding

class CuisineAdapter(private val cuisines: List<Cuisine>): RecyclerView.Adapter<CuisineAdapter.CuisineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuisineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_cuisine, parent, false)
        return CuisineViewHolder(view)
    }

    override fun getItemCount() = cuisines.size

    override fun onBindViewHolder(holder: CuisineViewHolder, position: Int) {
        val cuisine = cuisines[position]
        holder.binding.apply {
            textCuisineName.text = cuisine.name

            Glide.with(holder.binding.root)
                .load(cuisine.imageUrl)
                .into(cuisineImage)
        }
    }

    class CuisineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCardCuisineBinding.bind(itemView)
    }
}