package ru.steelwave.steelwave.domain.useCase.user

import ru.steelwave.steelwave.domain.entity.user.UserModel
import ru.steelwave.steelwave.domain.repository.user.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(user: UserModel){
        repository.deleteUserUseCase(user)
    }

}