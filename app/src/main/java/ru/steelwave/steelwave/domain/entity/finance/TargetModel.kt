package ru.steelwave.steelwave.domain.entity.finance

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TargetModel(
    val id: Int = UNDEFINED_ID,
    var projectId: Int,
    var name: String,
    var collectedPrice: Int = 0,
    var totalPrice: Int,
    var isFinished: Boolean = false
): Parcelable{
    companion object{
        private const val UNDEFINED_ID = 0
    }
}
