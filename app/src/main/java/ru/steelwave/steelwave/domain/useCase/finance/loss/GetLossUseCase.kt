package ru.steelwave.steelwave.domain.useCase.finance.loss

import ru.steelwave.steelwave.domain.repository.finance.LossRepository
import java.sql.Date
import javax.inject.Inject

class GetLossUseCase @Inject constructor(private val repository: LossRepository) {

    suspend operator fun invoke(lossDate: Date) = repository.getLossUseCase(lossDate)

}