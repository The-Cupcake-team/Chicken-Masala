package com.cupcake.chickenmasala.ui.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.SearchCardViewBinding
import com.cupcake.chickenmasala.utill.setImage


class HorizontalRecipeAdapter(private var recipeList: List<Recipe>, private val listener : RecentFoodInteractionClickListener)
    : RecyclerView.Adapter<HorizontalRecipeAdapter.HorizontalRecipeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalRecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_card_view, parent, false)
        return HorizontalRecipeViewHolder(view)
    }
    override fun onBindViewHolder(holder: HorizontalRecipeViewHolder, position: Int) {
        val currentRecipe = recipeList[position]

        holder.binding.apply {
            saveCardImage.setImage(currentRecipe.imageUrl)
            dishName.text = currentRecipe.translatedRecipeName
            cuisineName.text = currentRecipe.cuisine
            prepareTime.text = currentRecipe.totalTimeInMin.toString()
            root.setOnClickListener { listener.onClickRecentFoodItem(currentRecipe.id) }
        }
    }

    override fun getItemCount() = recipeList.size

    class HorizontalRecipeViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val binding = SearchCardViewBinding.bind(viewItem)
    }

    fun setData(newRecipeList: List<Recipe>){
        recipeList = newRecipeList
        notifyDataSetChanged()
    }

}

