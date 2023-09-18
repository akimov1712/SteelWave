package ru.steelwave.steelwave.data.database.model.finance

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import java.sql.Date

@Entity(tableName = "transactions")
data class TransactionDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val projectId: Int,
    val date: Long,
    val isIncome: Boolean,
    var name: String,
    var count: Int
)
