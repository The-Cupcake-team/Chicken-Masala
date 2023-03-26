package com.cupcake.chickenmasala.data

import com.cupcake.chickenmasala.data.dataSource.DataSource
import com.cupcake.chickenmasala.data.model.HealthAdvice
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class RepositoryImpl(
    private val dataSource: DataSource
) : Repository {

    override fun getRecipes(): List<Recipe> {
        return dataSource.getRecipes()
    }

    override fun getHealthAdvices(): List<HealthAdvice> {
        return dataSource.getHealthAdvices()
    }

}