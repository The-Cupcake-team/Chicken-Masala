package com.cupcake.chickenmasala.usecase.home

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class GetFilteredFoodUseCase(
    private val repository: Repository
) {
    operator fun invoke(cuisine: String): List<Recipe> {
        return repository.getRecipes()
            .filter { cuisine in it.cuisine }
            .shuffled()
    }
}