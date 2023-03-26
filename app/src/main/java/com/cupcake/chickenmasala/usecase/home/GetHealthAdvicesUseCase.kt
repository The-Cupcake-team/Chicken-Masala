package com.cupcake.chickenmasala.usecase.home

import com.cupcake.chickenmasala.usecase.Repository

class GetHealthAdvicesUseCase(private val repository: Repository) {
    operator fun invoke(limit:Int) = repository.getHealthAdvices().shuffled().take(limit)
}