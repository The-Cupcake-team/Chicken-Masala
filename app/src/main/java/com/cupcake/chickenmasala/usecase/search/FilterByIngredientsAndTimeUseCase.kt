package com.cupcake.chickenmasala.usecase.search

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.DataManager

class FilterByIngredientsAndTimeUseCase(private val dataManager: DataManager) {
    operator fun invoke(
        ingredients: List<String>,
        timeRange: List<IntRange>
    ): List<Recipe> {
        return dataManager
            .getRecipes()
            .filter { recipe ->
                ingredients.all { recipe.doesContainIngredient(it) } &&
                        timeRange.any { recipe.isInRange(it) }
            }
            .takeIf { it.isNotEmpty() }
            ?: dataManager.getRecipes()
    }
}