package ru.steelwave.steelwave.domain.entity.project

import android.graphics.Bitmap
import ru.steelwave.steelwave.domain.entity.user.UserModel

data class ProjectModel(
    val id: Int,
    var name: String,
    var previewImage: Bitmap? = null,
    val dateRelease: Long,
    var trafic: Int = 0,
    var income: Int = 0
)
