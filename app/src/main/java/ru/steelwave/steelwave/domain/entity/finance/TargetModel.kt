package ru.steelwave.steelwave.domain.entity.finance

data class TargetModel(
    val id: Int,
    var collectedPrice: Int = 0,
    var totalPrice: Int,
    var isFinished: Boolean = false
)
