package com.cupcake.chickenmasala.data.model

data class Recipe(
    val translatedRecipeName: String,
    val translatedIngredients: List<String>,
    val totalTimeInMin: String,
    val cleanedIngredients: List<String>,
    val cuisine: String,
    val translatedInstructions: List<String>,
    val urlDetailsRecipe: String,
    val imageUrl: String,
    val ingredientCounts: String
)
