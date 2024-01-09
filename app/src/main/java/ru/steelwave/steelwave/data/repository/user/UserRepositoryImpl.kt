package ru.steelwave.steelwave.data.repository.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.steelwave.steelwave.data.database.dao.user.UserDao
import ru.steelwave.steelwave.data.mapper.user.UserMapper
import ru.steelwave.steelwave.domain.entity.user.UserModel
import ru.steelwave.steelwave.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val mapper: UserMapper,
    private val dao: UserDao
) : UserRepository {

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

    override fun getAllUserUseCase(projectId: Int, limit: Int): Flow<List<UserModel>> =
        dao.getUserList(projectId, limit).map {
            mapper.mapListDbModelToListEntity(it)
        }

    override suspend fun getTotalSalary(projectId: Int) = dao.getTotalSalary(projectId)
}