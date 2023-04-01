package com.cupcake.chickenmasala.ui.fragment.dishes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.RecipeCardVerticalBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter
import com.cupcake.chickenmasala.ui.util.OnItemClickListener
import com.cupcake.chickenmasala.utill.setImage

class DishesAdapter(private val listener: OnItemClickListener<Recipe>) :
    BaseAdapter<Recipe, RecipeCardVerticalBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> RecipeCardVerticalBinding =
        RecipeCardVerticalBinding::inflate

    override fun <T> areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<T>
    ) = getOldItems()[oldItemPosition].id == (newItems[newItemPosition] as Recipe).id

    override fun bindItem(binding: RecipeCardVerticalBinding, item: Recipe) {
        with(binding) {
            val margin = root.resources.getDimensionPixelSize(R.dimen.spacing_small)
            (root.layoutParams as ViewGroup.MarginLayoutParams).setMargins(
                margin,
                margin,
                margin,
                margin
            )
            cardImage.setImage(item.imageUrl)
            recipeName.text = item.translatedRecipeName
            cuisine.text = item.cuisine
            time.text = item.formattedTime
            root.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}