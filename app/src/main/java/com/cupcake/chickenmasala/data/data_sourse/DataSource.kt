package com.cupcake.chickenmasala.data.data_sourse

import com.cupcake.chickenmasala.data.model.HealthyAdvice
import com.cupcake.chickenmasala.data.model.Recipe

interface DataSource {
    fun getRecipes(): List<Recipe>
    fun getHealthyAdvices(): List<HealthyAdvice>
}