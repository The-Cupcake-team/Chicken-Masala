package com.cupcake.chickenmasala.usecase
import com.cupcake.chickenmasala.data.model.HealthyAdvices
import com.cupcake.chickenmasala.data.model.Recipe

interface Repository {
    fun getRecipes(): List<Recipe>
    fun getHealthAdvices(): List<HealthyAdvices>
}