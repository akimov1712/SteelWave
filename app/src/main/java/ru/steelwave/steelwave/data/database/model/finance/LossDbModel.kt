package ru.steelwave.steelwave.data.database.model.finance

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
@Entity(tableName = "losses")
data class LossDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val projectId: Int,
    val date: Long,
    var detailedIncome: List<TransactionDbModel>
)
