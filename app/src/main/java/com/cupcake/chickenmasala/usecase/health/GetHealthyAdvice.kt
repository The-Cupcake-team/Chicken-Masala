package com.cupcake.chickenmasala.usecase.health

import com.cupcake.chickenmasala.data.DataManager

class GetHealthyAdvice(private val dataManager: DataManager) {
    fun invoke() = dataManager.getHealthyAdvice()
}