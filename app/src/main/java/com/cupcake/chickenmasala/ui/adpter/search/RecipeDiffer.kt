package com.cupcake.chickenmasala.ui.adpter.search

import androidx.recyclerview.widget.DiffUtil
import com.cupcake.chickenmasala.data.model.Recipe

class RecipeDiffer(private val oldRecipes: List<Recipe>, private val newRecipes: List<Recipe>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldRecipes.size

    override fun getNewListSize() = newRecipes.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldRecipes[oldItemPosition].id == newRecipes[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldRecipes[oldItemPosition] == newRecipes[newItemPosition]
}