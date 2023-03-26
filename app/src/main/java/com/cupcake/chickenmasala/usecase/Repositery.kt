package com.cupcake.chickenmasala.usecase
import com.cupcake.chickenmasala.data.model.HealthAdvice
import com.cupcake.chickenmasala.data.model.Recipe

interface Repository {
    fun getRecipes(): List<Recipe>
    fun getHealthAdvices(): List<HealthAdvice>
}