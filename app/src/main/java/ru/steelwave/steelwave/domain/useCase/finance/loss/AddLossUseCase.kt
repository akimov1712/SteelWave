package ru.steelwave.steelwave.domain.useCase.finance.loss

import ru.steelwave.steelwave.domain.entity.finance.LossModel
import ru.steelwave.steelwave.domain.repository.finance.LossRepository

class AddLossUseCase(private val repository: LossRepository) {

    suspend operator fun invoke(loss: LossModel){
        repository.addLossUseCase(loss)
    }

}