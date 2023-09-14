package ru.steelwave.steelwave.data.database.model.finance

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "targets")
data class TargetDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var projectId: Int,
    var name: String,
    var collectedPrice: Int = 0,
    var totalPrice: Int,
    var isFinished: Boolean = false
)
