package ru.steelwave.steelwave.data.database.model.traffic

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.steelwave.steelwave.Const
import java.util.Date

@Entity(tableName = "transfers")
data class TransferDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val projectId: Int,
    val name: String,
    val count: Int,
    val startDate: Long,
    val endDate: Long,
)
