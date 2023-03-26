package com.cupcake.chickenmasala.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.RecipeCardHorizontalBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter
import com.cupcake.chickenmasala.ui.base.OnItemClickListener
import com.cupcake.chickenmasala.utill.setImage

class VerticalRecipeRecyclerAdapter(private val listener: OnItemClickListener<Recipe>) : BaseAdapter<Recipe, RecipeCardHorizontalBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> RecipeCardHorizontalBinding
        get() = RecipeCardHorizontalBinding::inflate

    override fun <T> areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<T>
    ) =
        getOldItems()[oldItemPosition].id == (newItems[newItemPosition] as Recipe).id

    override fun bindItem(binding: RecipeCardHorizontalBinding, item: Recipe) {
        with(binding){
            cuisineName.text = item.cuisine
            recipeName.text = item.translatedRecipeName
            timer.text = item.totalTimeInMin.toString()
            cardImage.setImage(item.imageUrl)
            root.setOnClickListener{
                listener.onItemClicked(item)
            }
        }
    }
}