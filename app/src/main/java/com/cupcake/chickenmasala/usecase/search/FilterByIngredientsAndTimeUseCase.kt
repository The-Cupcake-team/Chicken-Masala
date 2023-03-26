package com.cupcake.chickenmasala.usecase.search

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class FilterByIngredientsAndTimeUseCase(private val repository: Repository) {
    operator fun invoke(
        ingredients: List<String>,
        timeRange: List<IntRange>,
        limit: Int = 100
    ): List<Recipe> {
        return repository
            .getRecipes()
            .filter { recipe ->
                ingredients.all { recipe.doesContainIngredient(it) } &&
                        timeRange.any { recipe.isInRange(it) }
            }
            .take(limit)
            .takeIf { it.isNotEmpty() }
            ?: repository.getRecipes().take(limit)
    }
}