package ru.steelwave.steelwave.domain.entity.user

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.steelwave.steelwave.domain.entity.project.ProjectModel

@Parcelize
data class UserModel(
    val id: Int,

    var login: String,
    var password: String,
    var secretWord: String,

    var firstName: String,
    var secondName: String,
    var middleName: String,

    var avatar: Bitmap? = null,
    var position: String,
    var project: ProjectModel,
    var salary: Int
): Parcelable
