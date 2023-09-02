package ru.steelwave.steelwave.domain.useCase.finance.target

import javax.inject.Inject

class GetAllTargetUseCase @Inject constructor(private val repository: TargetRepository) {

    operator fun invoke() = repository.getAllTargetUseCase()

}