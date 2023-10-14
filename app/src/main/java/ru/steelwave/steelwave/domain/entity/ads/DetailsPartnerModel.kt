package ru.steelwave.steelwave.domain.entity.ads

import ru.steelwave.steelwave.Const
import java.util.Date

data class DetailsPartnerModel(
    val id: Int = Const.UNDEFINED_ID,
    val partnerId: Int,
    val priceOfClick: Double,
    val startDateContract: Date,
    val endDateContract: Date,
)
