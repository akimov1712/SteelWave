package ru.steelwave.steelwave.domain.entity.traffic

import ru.steelwave.steelwave.Const
import java.util.Date

data class VisitionModel(
    val id: Int = Const.UNDEFINED_ID,
    val projectId: Int,
    val date: Date,
    val totalVisition: Int,
    val viewPages: Int,
    val viewPagesInVisit: Int,
    val visits15Seconds: Int,
    val averageTime: Int
)
