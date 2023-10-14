package ru.steelwave.steelwave.data.database.model.ads

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.steelwave.steelwave.Const
import java.util.Date

@Entity(tableName = "detailsPartners")
data class DetailsPartnerDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val partnerId: Int,
    val priceOfClick: Double,
    val startDateContract: Long,
    val endDateContract: Long,
)
