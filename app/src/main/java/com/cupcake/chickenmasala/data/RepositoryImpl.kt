package com.cupcake.chickenmasala.data

import com.cupcake.chickenmasala.data.data_sourse.DataSource
import com.cupcake.chickenmasala.data.model.HealthyAdvice
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class RepositoryImpl(
    private val dataSource: DataSource
) : Repository {

    override fun getRecipes(): List<Recipe> {
        return dataSource.getRecipes()
    }

    override fun getHealthyAdvices(): List<HealthyAdvice> {
        return dataSource.getHealthyAdvices()
    }

}