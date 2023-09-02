package ru.steelwave.steelwave.domain.useCase.user

import ru.steelwave.steelwave.domain.repository.user.UserRepository

class GetAllUserUseCase(private val repository: UserRepository) {

    operator fun invoke() = repository.getAllUserUseCase()

}