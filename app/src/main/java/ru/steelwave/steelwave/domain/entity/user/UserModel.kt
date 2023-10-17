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
    var projectId: Int,
    var salary: Int,
    var percentSalaryProject: Int = 0
): Parcelable {

    fun setPercentSalary(totalSalary: Int){
        val percent: Int = ((salary.toDouble() / totalSalary.toDouble()) * 100).toInt()
        percentSalaryProject = percent
    }

    companion object {
        private const val UNDEFINED_ID = 0
    }
}
