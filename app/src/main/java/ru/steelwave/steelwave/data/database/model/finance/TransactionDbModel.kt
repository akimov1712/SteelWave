package ru.steelwave.steelwave.data.database.model.finance

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel

data class TransactionDbModel(
    var name: String,
    var count: Int
)
