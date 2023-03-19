package com.cupcake.chickenmasala.utill

import com.cupcake.chickenmasala.data.domain.Recipe

class CsvParser {
    fun parse(line: String): Recipe {
        val tokens = line.split(",")
        return Recipe(
            translatedRecipeName = tokens[Constants.ColumnIndex.TRANSLATED_RECIPE_NAME],
            translatedIngredients = tokens[Constants.ColumnIndex.TRANSLATED_INGREDIENTS].split(";"),
            cleanedIngredients = tokens[Constants.ColumnIndex.CLEANED_INGREDIENTS].split(";"),
            totalTimeInMin = tokens[Constants.ColumnIndex.TOTAL_TIME_IN_MIN].toInt(),
            cuisine = tokens[Constants.ColumnIndex.CUISINE],
            translatedInstructions = tokens[Constants.ColumnIndex.TRANSLATED_INSTRUCTIONS].lines(),
            url = tokens[Constants.ColumnIndex.URL],
            imageUrl = tokens[Constants.ColumnIndex.IMAGEURL],
            ingredientCounts = tokens[Constants.ColumnIndex.INGREDIENT_COUNTS].toInt()
        )
    }
}