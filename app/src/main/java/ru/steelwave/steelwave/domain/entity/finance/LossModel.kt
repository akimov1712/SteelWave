package ru.steelwave.steelwave.domain.entity.finance

import java.sql.Date

data class LossModel(
    val id: Int = UNDEFINED_ID,
    val projectId: Int,
    val date: Long,
    var detailedIncome: List<TransactionModel>
){
    companion object{
        private const val UNDEFINED_ID = 0
    }
}
