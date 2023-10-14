package ru.steelwave.steelwave.domain.entity.ads

import android.graphics.Bitmap
import ru.steelwave.steelwave.Const
import java.util.Date

data class PartnerModel(
    val id: Int = Const.UNDEFINED_ID,
    val projectId: Int,
    val name: String,
    val preview: Bitmap?,
    val contractEndDate: Date
)
