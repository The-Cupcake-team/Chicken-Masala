package com.cupcake.chickenmasala.usecase.cuisine

import com.cupcake.chickenmasala.data.model.Cuisine
import com.cupcake.chickenmasala.usecase.Repository

class GetCuisineUseCase(private val repository: Repository) {

    operator fun invoke(): List<Cuisine> {
        return repository.getRecipes()
            .groupBy { it.cuisine }
            .map {
                Cuisine(
                    key = it.key,
                    imageUrl = it.value.shuffled().first().imageUrl,
                    name = it.key.replace("Recipes", ""),
                )
            }
    }
}