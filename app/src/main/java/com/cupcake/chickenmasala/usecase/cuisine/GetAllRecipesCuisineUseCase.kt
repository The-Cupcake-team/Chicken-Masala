package com.cupcake.chickenmasala.usecase.cuisine

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class GetAllRecipesCuisineUseCase(private val repository: Repository) {
    operator fun invoke(cuisine: String): List<Recipe> {
        return repository
            .getRecipes()
            .groupBy { it.cuisine }[cuisine]!!.shuffled()
    }
}