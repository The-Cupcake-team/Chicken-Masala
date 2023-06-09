package com.cupcake.chickenmasala.data.dataSource

import com.cupcake.chickenmasala.data.model.HealthAdvice
import com.cupcake.chickenmasala.data.model.Recipe

interface DataSource {
    fun getRecipes(): List<Recipe>
    fun getHealthAdvices(): List<HealthAdvice>
}