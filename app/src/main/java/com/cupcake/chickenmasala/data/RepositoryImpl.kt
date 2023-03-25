package com.cupcake.chickenmasala.data

import com.cupcake.chickenmasala.data.data_source.DataSource
import com.cupcake.chickenmasala.data.model.HealthyAdvices
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class RepositoryImpl(
    private val dataSource: DataSource
) : Repository {

    override fun getRecipes(): List<Recipe> {
        return dataSource.getRecipes()
    }

    override fun getHealthAdvices(): List<HealthyAdvices> {
        return dataSource.getHealthAdvices()
    }

}