package ru.steelwave.steelwave.domain.useCase.finance

import ru.steelwave.steelwave.domain.repository.finance.TargetRepository
import javax.inject.Inject

class GetAllTargetUseCase @Inject constructor(private val repository: TargetRepository) {

    operator fun invoke(projectId: Int) = repository.getAllTarget(projectId)

}