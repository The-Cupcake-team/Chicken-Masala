package com.cupcake.chickenmasala.data

import com.cupcake.chickenmasala.data.model.HealthyAdvices
import com.cupcake.chickenmasala.data.model.Recipe

interface DataManager {
    fun getRecipes(): List<Recipe>
    fun getHealthyAdvice(): List<HealthyAdvices>
}