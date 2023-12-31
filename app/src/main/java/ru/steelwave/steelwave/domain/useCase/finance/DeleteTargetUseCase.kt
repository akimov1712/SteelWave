package ru.steelwave.steelwave.domain.useCase.finance

import ru.steelwave.steelwave.domain.repository.finance.TargetRepository
import javax.inject.Inject

class DeleteTargetUseCase @Inject constructor(
    private val repository: TargetRepository
){

    suspend operator fun invoke(targetId: Int) = repository.deleteTarget(targetId)

}