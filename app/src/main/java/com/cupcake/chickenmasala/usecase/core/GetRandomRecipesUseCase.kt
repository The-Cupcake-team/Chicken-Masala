package com.cupcake.chickenmasala.usecase.core

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class GetRandomRecipesUseCase(private val repository: Repository) {
    operator fun invoke(limit: Int): List<Recipe> {
        return repository.getRecipes().shuffled().take(limit)
    }
}