package com.cupcake.chickenmasala.usecase.search

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class SearchByRecipeNameUseCase(private val repository: Repository) {
    operator fun invoke(recipeName: String, limit: Int = 100): List<Recipe>? {
        return repository
            .getRecipes()
            .filter { it.doesMatchRecipeName(recipeName) }
            .take(limit)
            .takeIf { it.isNotEmpty() }
    }
}