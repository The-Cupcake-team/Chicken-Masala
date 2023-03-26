package com.cupcake.chickenmasala.usecase.search

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class FilterByIngredientsAndTimeUseCase(private val repository: Repository) {
    operator fun invoke(
        ingredients: List<String>,
        timeRange: List<IntRange>
    ): List<Recipe> {
        return repository
            .getRecipes()
            .filter { recipe ->
                ingredients.all { recipe.doesContainIngredient(it) } &&
                        timeRange.any { recipe.isInRange(it) }
            }
            .takeIf { it.isNotEmpty() }
            ?: repository.getRecipes()
    }
}