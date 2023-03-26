package com.cupcake.chickenmasala.ui.fragment.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.SearchCardViewBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter
import com.cupcake.chickenmasala.ui.util.OnItemClickListener
import com.cupcake.chickenmasala.utill.setImage


class HorizontalRecipeRecyclerAdapter(private val listener: OnItemClickListener<Recipe>) :
    BaseAdapter<Recipe, SearchCardViewBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    SearchCardViewBinding = SearchCardViewBinding::inflate

    override fun <T> areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<T>
    ) = getOldItems()[oldItemPosition].id == (newItems[newItemPosition] as Recipe).id

    override fun bindItem(binding: SearchCardViewBinding, item: Recipe) {
        with(binding) {
            saveCardImage.setImage(item.imageUrl)
            dishName.text = item.translatedRecipeName
            cuisineName.text = item.cuisine
            prepareTime.text = item.totalTimeInMin.toString()
            root.setOnClickListener{
                listener.onItemClicked(item)
            }
        }
    }


}