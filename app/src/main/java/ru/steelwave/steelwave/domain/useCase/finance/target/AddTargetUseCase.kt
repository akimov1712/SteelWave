package ru.steelwave.steelwave.domain.useCase.finance.target

import ru.steelwave.steelwave.domain.entity.finance.TargetModel
import ru.steelwave.steelwave.domain.repository.finance.TargetRepository

class AddTargetUseCase(private val repository: TargetRepository) {

    suspend operator fun invoke(target: TargetModel){
        repository.addTargetUseCase(target)
    }

}