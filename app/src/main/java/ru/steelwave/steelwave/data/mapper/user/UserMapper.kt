package ru.steelwave.steelwave.data.mapper.user

import ru.steelwave.steelwave.data.database.model.user.UserDbModel
import ru.steelwave.steelwave.data.mapper.project.ProjectMapper
import ru.steelwave.steelwave.domain.entity.user.UserModel
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun mapEntityToDbModel(user: UserModel) = UserDbModel(
        id = user.id,
        login = user.login,
        password = user.password,
        secretWord = user.secretWord,
        firstName = user.firstName,
        secondName = user.lastName,
        middleName = user.middleName,
        avatar = user.avatar,
        position = user.position,
        salary = user.salary,
        projectId = user.projectId,
        percentSalaryProject = user.percentSalaryProject
    )

    fun mapDbModelToEntity(user: UserDbModel) = UserModel(
        id = user.id,
        login = user.login,
        password = user.password,
        secretWord = user.secretWord,
        firstName = user.firstName,
        lastName = user.secondName,
        middleName = user.middleName,
        avatar = user.avatar,
        position = user.position,
        salary = user.salary,
        projectId = user.projectId,
        percentSalaryProject = user.percentSalaryProject
    )

    fun mapListDbModelToListEntity(userList: List<UserDbModel>) = userList.map {
        mapDbModelToEntity(it)
    }

    fun mapListEntityToListDbModel(userList: List<UserModel>) = userList.map {
        mapEntityToDbModel(it)
    }

}