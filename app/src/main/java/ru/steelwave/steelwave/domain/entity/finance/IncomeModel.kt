package ru.steelwave.steelwave.domain.entity.finance

data class IncomeModel(
    val totalIncome: Int,
    val projectProfit: Int,
    val detailedIncome: Map<String, Int>? = null
)
