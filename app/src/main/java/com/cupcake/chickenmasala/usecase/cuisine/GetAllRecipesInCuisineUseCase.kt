package com.cupcake.chickenmasala.usecase.cuisine

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class GetAllRecipesInCuisineUseCase(private val dataSource: Repository) {
    operator fun invoke(cuisine: String): List<Recipe> {
        return dataSource
            .getRecipes()
            .groupBy { it.cuisine }[cuisine]!!
    }
}