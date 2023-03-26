package com.cupcake.chickenmasala.usecase

import com.cupcake.chickenmasala.data.model.Recipe

class GetAllRecipesInCuisineUseCase(private val dataSource: List<Recipe>) {
    operator fun invoke(cuisine: String): List<Recipe> {
        return dataSource
            .groupBy { it.cuisine }[cuisine]!!
    }
}