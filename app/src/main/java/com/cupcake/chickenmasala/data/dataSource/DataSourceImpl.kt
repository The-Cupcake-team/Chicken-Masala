package com.cupcake.chickenmasala.data.data_sourse

import android.app.Application
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.dataSource.DataSource
import com.cupcake.chickenmasala.data.model.HealthAdvice
import com.cupcake.chickenmasala.data.model.Recipe
import java.io.InputStreamReader

class DataSourceImpl(private val context: Application) : DataSource {

    private fun parseFile(inputStreamReader: InputStreamReader): MutableMap<Int, List<String>> {
        val data = mutableMapOf<Int, List<String>>()
        inputStreamReader.readLines().forEachIndexed { index, item ->
            val tokens = item.split(",")
            data[index] = tokens
        }
        return data
    }

    private fun openFile(fileName: String): InputStreamReader {
        val inputStream = context.assets.open(fileName)
        return InputStreamReader(inputStream)
    }

    override fun getRecipes(): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        val fileReader = openFile(INDIAN_FOOD_FILE_PATH)
        parseFile(fileReader).forEach { (key, data) ->
            recipes.add(
                Recipe(
                    id = key,
                    translatedRecipeName = data[TRANSLATED_RECIPE_NAME],
                    translatedIngredients = data[TRANSLATED_INGREDIENTS].split(";"),
                    cleanedIngredients = data[CLEANED_INGREDIENTS].split(";"),
                    totalTimeInMin = data[TOTAL_TIME_IN_MIN].toInt(),
                    formattedTime = toFormattedTime(data[TOTAL_TIME_IN_MIN].toInt()),
                    cuisine = data[CUISINE],
                    translatedInstructions = data[TRANSLATED_INSTRUCTIONS].split(";"),
                    urlDetailsRecipe = data[URL],
                    imageUrl = data[IMAGE_RECIPE_URL],
                    ingredientCounts = data[INGREDIENT_COUNTS].toInt()
                )
            )
        }
        fileReader.close()
        return recipes
    }

    override fun getHealthAdvices(): List<HealthAdvice> {
        val advices = mutableListOf<HealthAdvice>()
        val fileReader = openFile(HEALTH_ADVICES_FILE_PATH)
        parseFile(fileReader).forEach { (key, data) ->
            advices.add(
                HealthAdvice(
                    id = key,
                    title = data[TITLE],
                    description = data[DESCRIPTION],
                    imageUrl = data[IMAGE_HEALTH_URL]
                )
            )
        }
        fileReader.close()
        return advices
    }

    private fun toFormattedTime(time: Int): String{
        val hours = time / 60
        val min = time % 60
        var formatTime = ""
        val hourChar = context.getString(R.string.hours_char)
        val minuteChar = context.getString(R.string.mimute_char)
        if (hours != 0){
            formatTime = "${hours}$hourChar"
        }
        if (min != 0){
            formatTime += "${min}$minuteChar"
        }else{
            formatTime = "0$minuteChar"
        }
        return formatTime
    }

    companion object {
        private const val TRANSLATED_RECIPE_NAME = 0
        private const val TRANSLATED_INGREDIENTS = 1
        private const val TOTAL_TIME_IN_MIN = 2
        private const val CUISINE = 3
        private const val TRANSLATED_INSTRUCTIONS = 4
        private const val URL = 5
        private const val CLEANED_INGREDIENTS = 6
        private const val IMAGE_RECIPE_URL = 7
        private const val INGREDIENT_COUNTS = 8


        private const val TITLE = 0
        private const val DESCRIPTION = 1
        private const val IMAGE_HEALTH_URL = 2

        private const val INDIAN_FOOD_FILE_PATH = "Indian_food.csv"
        private const val HEALTH_ADVICES_FILE_PATH = "health_advices.csv"
    }
}