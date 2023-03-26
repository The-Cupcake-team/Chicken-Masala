package com.cupcake.chickenmasala.ui.fragment.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.ItemChipBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter

class CleanIngredientsAdapter:BaseAdapter<String,ItemChipBinding>(){
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemChipBinding = ItemChipBinding::inflate

    override fun <T> areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<T>
    ) = getOldItems()[oldItemPosition] == (newItems[newItemPosition] as Recipe).translatedRecipeName

    override fun bindItem(binding: ItemChipBinding, item: String) {
        with(binding){
            chipIngredient.text = item
        }
    }
}