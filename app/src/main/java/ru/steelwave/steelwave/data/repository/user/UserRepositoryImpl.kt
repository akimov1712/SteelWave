package ru.steelwave.steelwave.data.repository.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.steelwave.steelwave.Const
import ru.steelwave.steelwave.data.database.dao.user.UserDao
import ru.steelwave.steelwave.data.mapper.user.UserMapper
import ru.steelwave.steelwave.domain.entity.user.UserModel
import ru.steelwave.steelwave.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val mapper: UserMapper,
    private val dao: UserDao
): UserRepository {

    override suspend fun getCountUsersUseCase(projectId: Int): Int {
        return dao.getCountUsers(projectId)
    }

    override suspend fun addUserUseCase(user: UserModel) {
        dao.addUser(mapper.mapEntityToDbModel(user))
    }

    override suspend fun deleteUserUseCase(userId: Int) {
        dao.deleteUser(userId)
    }

    override suspend fun getUserUseCase(userId: Int) =
        mapper.mapDbModelToEntity(dao.getUser(userId))

    override fun getAllUserUseCase(projectId: Int, limit: Int): LiveData<List<UserModel>> =
            Transformations.map(dao.getUserList(projectId, limit)){
                mapper.mapListDbModelToListEntity(it)
            }


}