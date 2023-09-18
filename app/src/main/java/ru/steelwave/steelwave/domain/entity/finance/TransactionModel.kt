package ru.steelwave.steelwave.domain.entity.finance

import java.sql.Date

data class TransactionModel(
    val id: Int = UNDEFINED_ID,
    val projectId: Int,
    val date: Date,
    val isIncome: Boolean,
    var name: String,
    var count: Int
){
    companion object{
        private const val UNDEFINED_ID = 0
    }
}
