package com.cupcake.chickenmasala.utill

import com.cupcake.chickenmasala.data.domain.Food

class CsvParser {
    fun parse(line: String): Food {
        val tokens = line.split(",")
        return Food(
            translatedRecipeName = tokens[Constants.ColumnIndex.TRANSLATEDRECIPENAME],
            translatedIngredients = tokens[Constants.ColumnIndex.TRANSLATEDINGREDIENTS],
            cleanedIngredients = tokens[Constants.ColumnIndex.CLEANED_INGREDIENTS],
            totalTimeInMin = tokens[Constants.ColumnIndex.TOTALTIMEINMIN].toInt(),
            cuisine = tokens[Constants.ColumnIndex.CUISINE],
            translatedInstructions = tokens[Constants.ColumnIndex.TRANSLATEDINSTRUCTIONS],
            url = tokens[Constants.ColumnIndex.URL],
            imageUrl = tokens[Constants.ColumnIndex.IMAGEURL],
            ingredientCounts = tokens[Constants.ColumnIndex.INGREDIENTCOUNTS].toInt()
        )
    }
}