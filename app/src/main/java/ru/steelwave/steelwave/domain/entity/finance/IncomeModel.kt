package ru.steelwave.steelwave.domain.entity.finance

import java.sql.Date

data class IncomeModel(
    val id: Int = UNDEFINED_ID,
    val projectId: Int,
    val date: Date,
    val totalIncome: Int,
    val projectProfit: Int,
    val detailedIncome: List<TransactionModel>
) {
    companion object{
        private const val UNDEFINED_ID = 0
    }
}
