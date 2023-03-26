package com.cupcake.chickenmasala.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.data.model.Cuisine
import com.cupcake.chickenmasala.databinding.ItemCardCuisineBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter
import com.cupcake.chickenmasala.utill.setImage

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
            cuisineImage.setImage(cuisine.imageUrl)

            root.setOnClickListener { listener.onClickCuisine(cuisine) }
        }
    }

    interface CuisineInteractionListener{
        fun onClickCuisine(cuisine: Cuisine)
    }
}