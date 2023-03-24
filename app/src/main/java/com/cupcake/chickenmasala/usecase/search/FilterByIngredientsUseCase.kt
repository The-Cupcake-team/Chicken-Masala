package com.cupcake.chickenmasala.usecase.search

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.DataManager

class FilterByIngredientsUseCase(private val dataManager: DataManager) {
    operator fun invoke(ingredients: List<String>): List<Recipe>? {
        return dataManager
            .getRecipes()
            .filter { recipe ->
                ingredients.all { recipe.doesContainIngredient(it) }
            }.takeIf { it.isNotEmpty() }
    }
}