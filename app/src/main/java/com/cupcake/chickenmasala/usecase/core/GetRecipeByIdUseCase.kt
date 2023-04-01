package com.cupcake.chickenmasala.usecase.core

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository

class GetRecipeByIdUseCase(val repository: Repository) {
    operator fun invoke(recipeId: Int): Recipe {
        return repository.getRecipes()[recipeId]
    }
}