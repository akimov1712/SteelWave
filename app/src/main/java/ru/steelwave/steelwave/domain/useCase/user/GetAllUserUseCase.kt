package ru.steelwave.steelwave.domain.useCase.user

import javax.inject.Inject

class GetAllUserUseCase @Inject constructor(private val repository: UserRepository) {

    operator fun invoke() = repository.getAllUserUseCase()

}