package com.cupcake.chickenmasala.utill.parsers

import com.cupcake.chickenmasala.data.model.Recipe

class CsvParser {
    fun parse(line: String): Recipe {
        val tokens = line.split(",")
        return Recipe(
            translatedRecipeName = tokens[translatedRecipeName],
            translatedIngredients = tokens[translatedIngredients].split(";"),
            cleanedIngredients = tokens[cleanedIngredients].split(";"),
            totalTimeInMin = tokens[totalTimeInMin].toInt(),
            cuisine = tokens[cuisine],
            translatedInstructions = tokens[translatedInstructions].lines(),
            urlDetailsRecipe = tokens[urlDetailsMeal],
            imageUrl = tokens[imageUrl],
            ingredientCounts = tokens[ingredientCounts].toInt()
        )
    }

    private val translatedRecipeName = 0
    private val translatedIngredients = 1
    private val cleanedIngredients = 6
    private val totalTimeInMin = 2
    private val cuisine = 3
    private val translatedInstructions = 4
    private val urlDetailsMeal = 5
    private val imageUrl = 7
    private val ingredientCounts = 8

}