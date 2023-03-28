package com.cupcake.chickenmasala.data.data_sourse

import android.app.Application
import com.cupcake.chickenmasala.data.dataSource.DataSource
import com.cupcake.chickenmasala.data.model.HealthAdvice
import com.cupcake.chickenmasala.data.model.Recipe
import java.io.InputStreamReader

class DataSourceImpl(private val context: Application) : DataSource {

    private fun parseFile(inputStreamReader: InputStreamReader): List<List<String>> =
        inputStreamReader.buffered().useLines { it.map { line -> line.split(",") }.toList() }



    private fun openFile(fileName: String): InputStreamReader =
        InputStreamReader(context.assets.open(fileName))
    override fun getRecipes(): List<Recipe> =
        openFile(INDIAN_FOOD_FILE_PATH).use { fileReader ->
            parseFile(fileReader).mapIndexed { key, data ->
                Recipe(
                    id = key,
                    translatedRecipeName = data[TRANSLATED_RECIPE_NAME],
                    translatedIngredients = data[TRANSLATED_INGREDIENTS].split(";"),
                    cleanedIngredients = data[CLEANED_INGREDIENTS].split(";"),
                    totalTimeInMin = data[TOTAL_TIME_IN_MIN].toInt(),
                    cuisine = data[CUISINE],
                    translatedInstructions = data[TRANSLATED_INSTRUCTIONS].split(";"),
                    urlDetailsRecipe = data[URL],
                    imageUrl = data[IMAGE_URL],
                    ingredientCounts = data[INGREDIENT_COUNTS].toInt()
                )
            }
        }
    override fun getHealthAdvices(): List<HealthAdvice> =
        openFile(HEALTH_ADVICES_FILE_PATH).use { fileReader ->
            parseFile(fileReader).mapIndexed { key, data ->
                HealthAdvice(
                    id = key,
                    title = data[TITLE],
                    description = data[DESCRIPTION],
                    imageUrl = data[IMAGEURL]
                )
            }
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
        private const val IMAGEURL = 2

        private const val INDIAN_FOOD_FILE_PATH = "Indian_food.csv"
        private const val HEALTH_ADVICES_FILE_PATH = "health_advices.csv"
    }
}