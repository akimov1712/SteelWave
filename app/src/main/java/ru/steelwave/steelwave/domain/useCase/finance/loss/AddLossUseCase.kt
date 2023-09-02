package ru.steelwave.steelwave.domain.useCase.finance.loss

import ru.steelwave.steelwave.domain.entity.finance.LossModel
import ru.steelwave.steelwave.domain.repository.finance.LossRepository
import javax.inject.Inject

class AddLossUseCase @Inject constructor(private val repository: LossRepository) {

    suspend operator fun invoke(loss: LossModel){
        repository.addLossUseCase(loss)
    }

}