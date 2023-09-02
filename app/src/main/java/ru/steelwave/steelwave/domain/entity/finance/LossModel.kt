package ru.steelwave.steelwave.domain.entity.finance

data class LossModel(
    val totalLoss: Int,
    val detailedLoss: Map<String, Int>? = null
)
