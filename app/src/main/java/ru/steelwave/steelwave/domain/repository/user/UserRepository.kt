package ru.steelwave.steelwave.domain.repository.user

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ru.steelwave.steelwave.domain.entity.user.UserModel

interface UserRepository {

    suspend fun addUserUseCase(user: UserModel)
    suspend fun deleteUserUseCase(userId: Int)
    suspend fun getUserUseCase(userId: Int): UserModel
    suspend fun getCountUsersUseCase(projectId: Int): Int
    suspend fun getTotalSalary(projectId: Int): Int
    fun getAllUserUseCase(projectId: Int, limit: Int): Flow<List<UserModel>>

}