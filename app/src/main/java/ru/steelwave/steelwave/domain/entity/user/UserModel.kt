package ru.steelwave.steelwave.domain.entity.user

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.steelwave.steelwave.domain.entity.project.ProjectModel

@Parcelize
data class UserModel(
    val id: Int = UNDEFINED_ID,

    var login: String,
    var password: Int,
    var secretWord: String,

    var firstName: String,
    var lastName: String,
    var middleName: String,

    var avatar: Bitmap? = null,
    var position: String,
    var project: String,
    var salary: Int
): Parcelable {
    companion object {
        private const val UNDEFINED_ID = 0
    }
}
