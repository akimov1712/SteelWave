package ru.steelwave.steelwave.domain.entity.traffic

import ru.steelwave.steelwave.Const
import java.util.Date

data class TransferModel(
    val id: Int = Const.UNDEFINED_ID,
    val projectId: Int,
    val name: String,
    val count: Int,
    val startDate: Date,
    val endDate: Date,
)
