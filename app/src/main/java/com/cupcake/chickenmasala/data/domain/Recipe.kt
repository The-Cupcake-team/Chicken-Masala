package com.cupcake.chickenmasala.data.domain

data class Recipe(
    val translatedRecipeName: String,
    val translatedIngredients: List<String>,
    val totalTimeInMin: Int,
    val cleanedIngredients: List<String>,
    val cuisine: String,
    val translatedInstructions: List<String>,
    val url: String,
    val imageUrl: String,
    val ingredientCounts: Int
)
