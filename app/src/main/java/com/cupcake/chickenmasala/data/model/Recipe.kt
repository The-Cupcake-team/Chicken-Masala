package com.cupcake.chickenmasala.data.model

data class Recipe(
    val id: Int,
    val translatedRecipeName: String,
    val translatedIngredients: List<String>,
    val totalTimeInMin: Int,
    val formattedTime: String,
    val cleanedIngredients: List<String>,
    val cuisine: String,
    val translatedInstructions: List<StepInstructions>,
    val urlDetailsRecipe: String,
    val imageUrl: String,
    val ingredientCounts: Int
)