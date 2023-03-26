package com.cupcake.chickenmasala.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Cuisine
import com.cupcake.chickenmasala.databinding.ItemCardCuisineBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter

class CuisineAdapter(private val listener :CuisineAdapter.CuisineInteractionListener)
    : BaseAdapter<Cuisine, ItemCardCuisineBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemCardCuisineBinding
            = ItemCardCuisineBinding::inflate

    override fun <T> areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<T> ) = getOldItems()[oldItemPosition].key == (newItems[newItemPosition] as Cuisine).key


    override fun bindItem(binding: ItemCardCuisineBinding, cuisine: Cuisine) {
        with(binding){
            textCuisineName.text = cuisine.name
            Glide.with(binding.root)
                .load(cuisine.imageUrl)
                .into(cuisineImage)

            root.setOnClickListener { listener.onClickCuisine(cuisine) }
        }
    }

    interface CuisineInteractionListener{
        fun onClickCuisine(cuisine: Cuisine)
    }
}