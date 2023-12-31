package ru.steelwave.steelwave.domain.useCase.user.user

import ru.steelwave.steelwave.domain.entity.user.UserModel
import ru.steelwave.steelwave.domain.repository.user.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(userId: Int){
        repository.deleteUserUseCase(userId)
    }

}