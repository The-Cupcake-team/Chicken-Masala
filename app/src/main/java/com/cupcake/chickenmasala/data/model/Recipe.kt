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
)
