package com.cupcake.chickenmasala.data.domain

data class Food(
    val translatedRecipeName: String,
    val translatedIngredients: String,
    val totalTimeInMin: Int,
    val cleanedIngredients: String,
    val cuisine: String,
    val translatedInstructions: String,
    val url: String,
    val imageUrl: String,
    val ingredientCounts: Int
)
