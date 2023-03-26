package com.cupcake.chickenmasala.usecase.home

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class GetFilteredIngredientFoodUseCase(
    private val repository: Repository
) {
    operator fun invoke(ingredient: String): List<Recipe> {
        return repository.getRecipes()
            .filter { ingredient in it.cleanedIngredients }
            .takeIf { it.isNotEmpty() }
            ?: repository.getRecipes().shuffled()
    }
}