package com.cupcake.chickenmasala.data

import com.cupcake.chickenmasala.data.model.HealthyAdvices
import com.cupcake.chickenmasala.data.model.Recipe

class DataManagerImpl(
    private val parseIndianFile: List<Recipe>,
    private val parseHealthAdviceFile: List<HealthyAdvices>
) : DataManager {

    override fun getRecipes(): List<Recipe> {
        return parseIndianFile
    }

    override fun getHealthyAdvice(): List<HealthyAdvices> {
        return parseHealthAdviceFile
    }

}