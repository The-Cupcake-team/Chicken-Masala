package com.cupcake.chickenmasala.ui.fragment.cuisine

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.ItemCardCuisineBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter
import com.cupcake.chickenmasala.ui.base.OnItemClickListener
import com.cupcake.chickenmasala.utill.setImage

class CuisineAdapter(private val listener: OnItemClickListener<Recipe>) : BaseAdapter<Recipe, ItemCardCuisineBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemCardCuisineBinding = ItemCardCuisineBinding::inflate

    override fun <T> areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<T>
    ) = getOldItems()[oldItemPosition].translatedRecipeName == (newItems[newItemPosition] as Recipe).translatedRecipeName

    override fun bindItem(binding: ItemCardCuisineBinding, item: Recipe) {
        with(binding) {
            cuisineImage.setImage(item.imageUrl)
            textCuisineName.text = item.translatedRecipeName
            cuisineImage.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }

}