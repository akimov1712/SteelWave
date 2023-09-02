package ru.steelwave.steelwave.domain.useCase.user

import ru.steelwave.steelwave.domain.repository.user.UserRepository

class GetUserUseCase(private val repository: UserRepository) {

    suspend operator fun invoke(userId: Int) = repository.getUserUseCase(userId)

}