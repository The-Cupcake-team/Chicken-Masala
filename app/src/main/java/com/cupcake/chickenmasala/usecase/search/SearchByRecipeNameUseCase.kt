package com.cupcake.chickenmasala.usecase.search

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class SearchByRecipeNameUseCase(private val repository: Repository) {
    operator fun invoke(recipeName: String): List<Recipe>? {
        return repository
            .getRecipes()
            .filter { it.doesMatchRecipeName(recipeName) }
            .takeIf { it.isNotEmpty() }
    }
}