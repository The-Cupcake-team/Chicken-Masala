package com.cupcake.chickenmasala.data

import com.cupcake.chickenmasala.data.model.HealthyAdvices
import com.cupcake.chickenmasala.data.model.Recipe

class DataManagerImpl(
    private val parseIndianFile: MutableList<Recipe>?,
    private val parseHealthAdviceFile: MutableList<HealthyAdvices>?
) : DataManager {

    override fun getRecipes(): MutableList<Recipe>? {
        return parseIndianFile
    }

    override fun getHealthyAdvice(): List<HealthyAdvices>? {
        return parseHealthAdviceFile
    }

}