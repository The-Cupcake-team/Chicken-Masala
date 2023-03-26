package com.cupcake.chickenmasala.data.model

data class Recipe(
    val id: Int,
    val translatedRecipeName: String,
    val translatedIngredients: List<String>,
    val totalTimeInMin: Int,
    val cleanedIngredients: List<String>,
    val cuisine: String,
    val translatedInstructions: List<String>,
    val urlDetailsRecipe: String,
    val imageUrl: String,
    val ingredientCounts: Int
) {
    fun doesMatchRecipeName(recipeName: String): Boolean {
        return translatedRecipeName.contains(recipeName, ignoreCase = true)
    }

    fun isInRange(intRange: IntRange): Boolean{
        return totalTimeInMin in intRange
    }

    fun doesContainIngredient(ingredient: String): Boolean{
        return cleanedIngredients.map { it.lowercase() }.contains(ingredient.lowercase())
    }
}
