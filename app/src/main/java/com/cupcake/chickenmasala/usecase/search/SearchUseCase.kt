package com.cupcake.chickenmasala.usecase.search

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class SearchUseCase(private val repository: Repository) {
    operator fun invoke(searchQuery: SearchQuery, limit: Int = 20): List<Recipe>? {
        return with(searchQuery) {
            repository
            .getRecipes()
            .filterByName(name)
            ?.filterByIngredientsAndTimeRange(ingredients, timeRanges)
            ?.shuffled()
            ?.take(limit)
        }
    }
}

private fun Recipe.doesMatchRecipeName(recipeName: String): Boolean {
    return translatedRecipeName.contains(recipeName, ignoreCase = true)
}

private fun Recipe.isInRange(intRange: IntRange): Boolean {
    return totalTimeInMin in intRange
}

private fun Recipe.doesContainIngredient(ingredient: String): Boolean {
    return cleanedIngredients.map { it.lowercase() }.contains(ingredient.lowercase())
}

private fun List<Recipe>.filterByName(recipeName: String): List<Recipe>? {
    return filter { it.doesMatchRecipeName(recipeName) }
        .takeIf { it.isNotEmpty() }
}

private fun List<Recipe>.filterByIngredientsAndTimeRange(
    ingredients: List<String>,
    timeRange: List<IntRange>
): List<Recipe>? {
    return filter { recipe ->
        ingredients.all { recipe.doesContainIngredient(it) } &&
                timeRange.any { recipe.isInRange(it) }
    }.takeIf { it.isNotEmpty() }
}

