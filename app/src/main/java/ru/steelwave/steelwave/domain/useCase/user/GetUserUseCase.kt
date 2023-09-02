package ru.steelwave.steelwave.domain.useCase.user

import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(userId: Int) = repository.getUserUseCase(userId)

}