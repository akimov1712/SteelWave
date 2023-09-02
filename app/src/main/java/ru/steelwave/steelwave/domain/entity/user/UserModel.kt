package ru.steelwave.steelwave.domain.entity.user

import ru.steelwave.steelwave.domain.entity.project.ProjectModel

data class UserModel(
    val id: Int,

    var login: String,
    var password: String,
    var secretWord: String,

    var firstName: String,
    var secondName: String,
    var middleName: String,

    var avatar: String? = null,
    var position: String,
    var project: ProjectModel,
    var salary: Int
)
