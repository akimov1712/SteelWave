package ru.steelwave.steelwave.data.database.model.finance

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("yearIncomes")
data class YearIncomeDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val projectId: Int,
    val year: Int,
    val yearIncomeList: List<TransactionDbModel>
)
