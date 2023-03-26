package com.cupcake.chickenmasala.ui.adpter.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.SearchCardBinding
import com.cupcake.chickenmasala.databinding.SearchCardSmallBinding

class SearchAdapter(
    private var recipes: List<Recipe>,
    private val recipeClickListener: RecipeClickListener
) :
    RecyclerView.Adapter<SearchAdapter.BaseSearchHolder>() {

    fun updateRecipes(newRecipes: List<Recipe>) {
        val diffResult = DiffUtil.calculateDiff(RecipeDiffer(recipes, newRecipes))
        recipes = newRecipes
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = recipes.size
    override fun onBindViewHolder(holder: BaseSearchHolder, position: Int) {
        holder.bind(recipes[position], recipeClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseSearchHolder {
        return when (viewType) {
            LARGE -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.search_card, parent, false)
                RecipeHolder(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.search_card_small, parent, false)
                SmallRecipeHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
         return if (position % 2 == 0) LARGE else SMALL
    }

    abstract class BaseSearchHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        abstract fun bind(recipe: Recipe, recipeClickListener: RecipeClickListener)
    }

    class SmallRecipeHolder(viewItem: View) : BaseSearchHolder(viewItem) {
        val binding = SearchCardSmallBinding.bind(viewItem)
        override fun bind(recipe: Recipe, recipeClickListener: RecipeClickListener) {
            with(binding) {
                textViewRecipeName.text = recipe.translatedRecipeName
                textViewRecipeCusine.text = recipe.cuisine
                textViewTotalTime.text = recipe.totalTimeInMin.toString()
                imageView.updateImage(recipe.imageUrl)
                root.setOnClickListener { recipeClickListener.onRecipeClick(recipe.id) }
            }
        }
    }

    class RecipeHolder(viewItem: View) : BaseSearchHolder(viewItem) {
        val binding = SearchCardBinding.bind(viewItem)
        override fun bind(recipe: Recipe, recipeClickListener: RecipeClickListener) {
            with(binding) {
                textViewRecipeName.text = recipe.translatedRecipeName
                textViewRecipeCusine.text = recipe.cuisine
                textViewTotalTime.text = recipe.totalTimeInMin.toString()
                imageView.updateImage(recipe.imageUrl)
                root.setOnClickListener { recipeClickListener.onRecipeClick(recipe.id) }
            }
        }
    }

    companion object {
        const val SMALL = 1911
        const val LARGE = 2001
    }
}

fun ImageView.updateImage(url: String) {
    Glide.with(context).load(url).into(this)
}