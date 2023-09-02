package ru.steelwave.steelwave.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.steelwave.steelwave.domain.entity.project.ProjectModel

@Entity(tableName = "users")
data class UserDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    var login: String,
    var password: String,
    var secretWord: String,

    var firstName: String,
    var secondName: String,
    var middleName: String,

    var avatar: String? = null,
    var position: String,
    var project: ProjectDbModel,
    var salary: Int
)
