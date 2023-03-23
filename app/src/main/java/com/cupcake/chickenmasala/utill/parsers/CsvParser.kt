package com.cupcake.chickenmasala.utill.parsers

import android.util.Log
import com.cupcake.chickenmasala.data.model.HealthyAdvices
import com.cupcake.chickenmasala.data.model.Recipe
import java.io.InputStreamReader

class CsvParser {

    fun parseIndianFile(inputStreamReader: InputStreamReader): MutableList<Recipe> {
        val data = mutableListOf<Recipe>()
        inputStreamReader.forEachLine {
            val tokens = it.split(",")
            data.add(parseRecipe(tokens))
        }
        return data
    }

    fun parseHealthAdviceFile(inputStreamReader: InputStreamReader): MutableList<HealthyAdvices> {
        val data = mutableListOf<HealthyAdvices>()
        inputStreamReader.forEachLine {
            val tokens = it.split(",")
            data.add(parseHealthy(tokens))
        }
        return data
    }

    private fun parseRecipe(line: List<String>): Recipe {
        Log.d("SearchScreen", "parseRecipe: $line")
        return Recipe(
            translatedRecipeName = line[translatedRecipeName],
            translatedIngredients = line[translatedIngredients].split(";"),
            totalTimeInMin = line[totalTimeInMin],
            cleanedIngredients = line[cleanedIngredients].split(";"),
            cuisine = line[cuisine],
            translatedInstructions = line[translatedInstructions].lines(),
            urlDetailsRecipe = line[urlDetailsMeal],
            imageUrl = line[imageUrl],
            ingredientCounts = line[ingredientCounts]
        )
    }

    private fun parseHealthy(line: List<String>): HealthyAdvices {
        return HealthyAdvices(
            id = line[0].toInt(),
            title = line[1],
            description = line[2],
        )
    }

    private val translatedRecipeName = 0
    private val translatedIngredients = 1
    private val totalTimeInMin = 2
    private val cuisine = 3
    private val translatedInstructions = 4
    private val urlDetailsMeal = 5
    private val cleanedIngredients = 6
    private val imageUrl = 7
    private val ingredientCounts = 8
}