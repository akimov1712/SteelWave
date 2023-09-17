package ru.steelwave.steelwave.data.database.model.finance

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Transaction
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel

@Entity(tableName = "losses")
data class LossDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val projectId: Int,
    val date: Long,
    var transactionList: List<TransactionDbModel> = listOf()
)
