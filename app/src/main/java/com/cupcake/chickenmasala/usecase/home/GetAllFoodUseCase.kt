package com.cupcake.chickenmasala.usecase.home

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class GetAllFoodUseCase(
    val repository: Repository
) {
    operator fun invoke(): List<Recipe> {
        return repository.getRecipes().shuffled()
    }
}