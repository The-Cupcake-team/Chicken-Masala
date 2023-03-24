package com.cupcake.chickenmasala.usecase.search

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.DataManager

class SearchByRecipeNameUseCase(private val dataManager: DataManager) {
    operator fun invoke(recipeName: String): List<Recipe>? {
        return dataManager
            .getRecipes()
            .filter { it.doesMatchRecipeName(recipeName) }
            .takeIf { it.isNotEmpty() }
    }
}