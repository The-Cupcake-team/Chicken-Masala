package com.cupcake.chickenmasala.usecase

import com.cupcake.chickenmasala.data.model.HealthyAdvice
import com.cupcake.chickenmasala.data.model.Recipe

interface Repository {
    fun getRecipes(): List<Recipe>
    fun getHealthyAdvices(): List<HealthyAdvice>
}