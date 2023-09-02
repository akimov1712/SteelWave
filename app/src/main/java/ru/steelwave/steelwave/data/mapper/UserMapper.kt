package ru.steelwave.steelwave.data.mapper

import ru.steelwave.steelwave.data.database.model.UserDbModel
import ru.steelwave.steelwave.domain.entity.user.UserModel
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun mapEntityToDbModel(user: UserModel) = UserDbModel(
        id = user.id,
        login = user.login,
        password = user.password,
        secretWord = user.secretWord,
        firstName = user.firstName,
        secondName = user.secondName,
        middleName = user.middleName,
        avatar = user.avatar,
        position = user.position,
        salary = user.salary,
        project = ProjectMapper().mapEntityToDbModel(user.project)
    )

    fun mapDbModelToEntity(user: UserDbModel) = UserModel(
        id = user.id,
        login = user.login,
        password = user.password,
        secretWord = user.secretWord,
        firstName = user.firstName,
        secondName = user.secondName,
        middleName = user.middleName,
        avatar = user.avatar,
        position = user.position,
        salary = user.salary,
        project = ProjectMapper().mapDbModelToEntity(user.project)
    )

    fun mapListDbModelToListEntity(userList: List<UserDbModel>) = userList.map {
        mapDbModelToEntity(it)
    }

    fun mapListEntityToListDbModel(userList: List<UserModel>) = userList.map {
        mapEntityToDbModel(it)
    }

}