package com.cupcake.chickenmasala.ui.fragment.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.IngredientsItemBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter

class IngredientsAdapter():BaseAdapter<String,IngredientsItemBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> IngredientsItemBinding = IngredientsItemBinding::inflate


    override fun <T> areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<T>,
    )= getOldItems()[oldItemPosition] == (newItems[newItemPosition] as Recipe).translatedRecipeName

    override fun bindItem(binding: IngredientsItemBinding, item: String) {
        with(binding){
            ingredientsName.text = item
        }
    }

}