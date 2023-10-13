package ru.steelwave.steelwave.domain.repository.user

import androidx.lifecycle.LiveData
import ru.steelwave.steelwave.domain.entity.user.UserModel

interface UserRepository {

    suspend fun addUserUseCase(user: UserModel)
    suspend fun deleteUserUseCase(user: UserModel)
    suspend fun getUserUseCase(userId: Int): UserModel
    suspend fun getCountUsersUseCase(projectId: Int): Int
    fun getAllUserUseCase(projectId: Int, limit: Int): LiveData<List<UserModel>>

}