package ru.steelwave.steelwave.domain.useCase.user.user

import ru.steelwave.steelwave.domain.repository.user.UserRepository
import javax.inject.Inject

class GetTotalSalaryUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(projectId: Int) = repository.getTotalSalary(projectId)

}