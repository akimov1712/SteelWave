package ru.steelwave.steelwave.domain.useCase.finance.target

import ru.steelwave.steelwave.domain.repository.finance.TargetRepository

class GetAllTargetUseCase(private val repository: TargetRepository) {

    operator fun invoke() = repository.getAllTargetUseCase()

}