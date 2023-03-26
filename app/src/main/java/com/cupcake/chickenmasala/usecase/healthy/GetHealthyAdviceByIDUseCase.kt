package com.cupcake.chickenmasala.usecase.healthy

import com.cupcake.chickenmasala.data.model.HealthAdvice
import com.cupcake.chickenmasala.usecase.Repository

class GetHealthyAdviceByIDUseCase(
    private val repository: Repository
) {

    operator fun invoke(id: Int): HealthAdvice {
        return repository.getHealthAdvices().first { it.id == id }
    }
}