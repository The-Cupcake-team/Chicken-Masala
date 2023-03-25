package com.cupcake.chickenmasala.usecase.home

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class FoodTypeFilterUseCase(
    private val repository: Repository
) {
    operator fun invoke(ingredient: String): List<Recipe> {
        return repository.getRecipes()
            .filter { ingredient in it.cleanedIngredients }
    }
}