package com.cupcake.chickenmasala.utill.parsers

import com.cupcake.chickenmasala.data.model.HealthAdvice
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.utill.TypeModel
import java.io.InputStreamReader

class CsvParser {

    fun parseFile(inputStreamReader: InputStreamReader, type: TypeModel): List<Any> {
        val data = mutableListOf<Any>()
        inputStreamReader.readLines().forEachIndexed { index, item ->
            val tokens = item.split(",")
            when (type) {
                TypeModel.RECIPE -> data.add(getRecipe(tokens, index))
                TypeModel.HEALTH_ADVICE -> data.add(getHealthyAdvice(tokens, index))
            }
        }
        return data
    }

    private fun getRecipe(line: List<String>, index: Int): Recipe {
        return Recipe(
            id = index,
            translatedRecipeName = line[TRANSLATED_RECIPE_NAME],
            translatedIngredients = line[TRANSLATED_INGREDIENTS].split(";"),
            cleanedIngredients = line[CLEANED_INGREDIENTS].split(";"),
            totalTimeInMin = line[TOTAL_TIME_IN_MIN].toInt(),
            cuisine = line[CUISINE],
            translatedInstructions = line[TRANSLATED_INSTRUCTIONS].split(";"),
            urlDetailsRecipe = line[URL],
            imageUrl = line[IMAGE_URL],
            ingredientCounts = line[INGREDIENT_COUNTS].toInt()
        )
    }

    private fun getHealthyAdvice(line: List<String>, index: Int): HealthAdvice {
        return HealthAdvice(
            id = index,
            title = line[TITLE],
            description = line[DESCRIPTION],
            imageUrl = line[IMAGE_URL_HEALTHY],
        )
    }

    companion object {
        private const val TRANSLATED_RECIPE_NAME = 0
        private const val TRANSLATED_INGREDIENTS = 1
        private const val TOTAL_TIME_IN_MIN = 2
        private const val CUISINE = 3
        private const val TRANSLATED_INSTRUCTIONS = 4
        private const val URL = 5
        private const val CLEANED_INGREDIENTS = 6
        private const val IMAGE_URL = 7
        private const val INGREDIENT_COUNTS = 8


        private const val TITLE = 0
        private const val DESCRIPTION = 1
        private const val IMAGE_URL_HEALTHY = 2
    }

}

