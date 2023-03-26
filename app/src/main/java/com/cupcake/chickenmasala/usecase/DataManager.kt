package com.cupcake.chickenmasala.usecase

import com.cupcake.chickenmasala.data.model.HealthAdvice
import com.cupcake.chickenmasala.data.model.Recipe

interface DataManager {
    fun getRecipes(): List<Recipe>
    fun getHealthyAdvice(): List<HealthAdvice>
}