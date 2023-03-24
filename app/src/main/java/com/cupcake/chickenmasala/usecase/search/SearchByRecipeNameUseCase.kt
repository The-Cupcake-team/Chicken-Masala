package com.cupcake.chickenmasala.usecase.search

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.DataManager

class SearchByRecipeNameUseCase(private val dataManager: DataManager) {
    operator fun invoke(recipeName: String, limit: Int = 10): List<Recipe>? {
        return dataManager
            .getRecipes()
            .filter { it.doesMathRecipeName(recipeName) }
            .take(limit)
            .takeIf { it.isNotEmpty() }
    }
}