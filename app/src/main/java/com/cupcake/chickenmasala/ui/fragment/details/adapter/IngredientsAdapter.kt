package com.cupcake.chickenmasala.ui.fragment.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.ItemIngredientsBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter

class IngredientsAdapter() : BaseAdapter<String, ItemIngredientsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemIngredientsBinding =
        ItemIngredientsBinding::inflate


    override fun <T> areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<T>,
    ) = getOldItems()[oldItemPosition] == (newItems[newItemPosition] as Recipe).translatedRecipeName

    override fun bindItem(binding: ItemIngredientsBinding, item: String) {
        with(binding) {
            ingredientsName.text = item
        }
    }

}