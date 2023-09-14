package ru.steelwave.steelwave.data.database.model.finance

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incomes")
data class IncomeDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val projectId: Int,
    val date: Long,
    val projectProfit: Int,
    val detailedIncome: List<TransactionDbModel>
)