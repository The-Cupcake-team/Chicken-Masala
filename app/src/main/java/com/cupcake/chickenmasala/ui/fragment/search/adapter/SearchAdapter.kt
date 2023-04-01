package com.cupcake.chickenmasala.ui.fragment.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.SearchCardBinding
import com.cupcake.chickenmasala.utill.setImage

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
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_card, parent, false)
        return when (viewType) {
            LARGE -> { RecipeHolder(view) }
            else -> { SmallRecipeHolder(view) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isEvenPosition(position)) LARGE else SMALL
    }

    private fun isEvenPosition(position: Int): Boolean {
        return position % 2 == 0
    }

    abstract class BaseSearchHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        abstract fun bind(recipe: Recipe, recipeClickListener: RecipeClickListener)
    }

    open class RecipeHolder(viewItem: View) : BaseSearchHolder(viewItem) {
        val binding = SearchCardBinding.bind(viewItem)
        override fun bind(recipe: Recipe, recipeClickListener: RecipeClickListener) {
            with(binding) {
                textViewRecipeName.text = recipe.translatedRecipeName
                textViewRecipeCuisine.text = recipe.cuisine
                textViewTotalTime.text = recipe.formattedTime
                imageView.setImage(recipe.imageUrl)
                root.setOnClickListener { recipeClickListener.onRecipeClick(recipe.id) }
            }
        }
    }

    class SmallRecipeHolder(viewItem: View) : RecipeHolder(viewItem) {
        override fun bind(recipe: Recipe, recipeClickListener: RecipeClickListener) {
            super.bind(recipe, recipeClickListener)
            (binding.root.layoutParams as ViewGroup.LayoutParams).height =
                itemView.resources.getDimensionPixelSize(R.dimen.small_search_card)
        }
    }

    companion object {
        const val SMALL = 1911
        const val LARGE = 2001
    }
}