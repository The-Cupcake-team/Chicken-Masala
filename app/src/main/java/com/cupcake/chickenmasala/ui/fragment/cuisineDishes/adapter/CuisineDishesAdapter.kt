package com.cupcake.chickenmasala.ui.fragment.cuisineDishes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.ItemCuisineDishesBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter
import com.cupcake.chickenmasala.utill.setImage

class CuisineDishesAdapter(private val listener: CuisineDishesInteractionListener) :
    BaseAdapter<Recipe, ItemCuisineDishesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemCuisineDishesBinding =
        ItemCuisineDishesBinding::inflate

    override fun <T> areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<T>
    ) = getOldItems()[oldItemPosition].id == (newItems[newItemPosition] as Recipe).id

    override fun bindItem(binding: ItemCuisineDishesBinding, item: Recipe) {
        with(binding) {
            textViewTime.text = item.totalTimeInMin.toString()
            textViewCuisine.text = item.ingredientCounts.toString()
            imageViewRecipeImage.setImage(item.imageUrl)
            root.setOnClickListener { listener.onClickCuisineDishes(item) }
        }
    }

    interface CuisineDishesInteractionListener {
        fun onClickCuisineDishes(recipe: Recipe)
    }
}