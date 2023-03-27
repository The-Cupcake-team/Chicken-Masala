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

    override fun getHistoryImagesUrl(): List<String> {
        return listOf(
            "https://user-images.githubusercontent.com/45900975/227811241-253de5bc-63ce-4175-a698-10c3a53dc07a.png",
            "https://user-images.githubusercontent.com/45900975/227811508-9020230d-bef4-455b-9e2b-f3dd1128fb84.png",
            "https://user-images.githubusercontent.com/45900975/227811628-fe6c8baf-e16e-4e3c-8839-88ec1b49acb8.png"
        )
    }


}