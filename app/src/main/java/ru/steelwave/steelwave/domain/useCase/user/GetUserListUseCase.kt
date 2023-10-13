package ru.steelwave.steelwave.domain.useCase.user

import ru.steelwave.steelwave.domain.repository.user.UserRepository
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val repository: UserRepository) {

    operator fun invoke(projectId: Int, limit: Int) = repository.getAllUserUseCase(projectId, limit)

}