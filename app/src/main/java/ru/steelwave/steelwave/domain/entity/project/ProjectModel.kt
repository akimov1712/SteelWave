package ru.steelwave.steelwave.domain.entity.project

import android.graphics.Bitmap
import ru.steelwave.steelwave.domain.entity.user.UserModel

data class ProjectModel(
    var name: String,
    var previewImage: Bitmap? = null,
    val dateRelease: Long,
    var trafic: Int = 0,
    var income: Int = 0,
    val id: Int = UNDEFINED_ID,
){
    companion object{
        private const val UNDEFINED_ID = 0
    }
}
