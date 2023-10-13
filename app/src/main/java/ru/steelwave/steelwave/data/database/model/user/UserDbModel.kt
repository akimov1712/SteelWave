package ru.steelwave.steelwave.data.database.model.user

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.steelwave.steelwave.data.database.model.project.ProjectDbModel

@Entity(tableName = "users")
data class UserDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    var login: String,
    var password: Int,
    var secretWord: String,

    var firstName: String,
    var secondName: String,
    var middleName: String,

    var avatar: Bitmap?,
    var position: String,
    var projectId: Int,
    var salary: Int
)
