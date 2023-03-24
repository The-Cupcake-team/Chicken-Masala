package com.cupcake.chickenmasala.ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.SearchCardViewBinding


class HomeAdapter(val list: List<Recipe>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_card_view, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentHome = list[position]

        holder.binding.apply {
            saveCardImage.setImage(currentHome.imageUrl)
            dishName.text = currentHome.translatedRecipeName
            cuisineName.text = currentHome.cuisine
            prepareTime.text = currentHome.totalTimeInMin.toString()

        }
    }

    private fun ImageView.setImage(url: String?) {
        Glide.with(context).load(url)
            .into(this)
    }

    class HomeViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val binding = SearchCardViewBinding.bind(viewItem)

    }


}

