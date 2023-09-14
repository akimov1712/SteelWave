package ru.steelwave.steelwave.domain.useCase.finance.loss

import ru.steelwave.steelwave.domain.repository.finance.LossRepository
import java.sql.Date
import javax.inject.Inject

class GetLossUseCase @Inject constructor(private val repository: LossRepository) {

    suspend operator fun invoke(projectId: Int, lossDate: Date) =
        repository.getLossUseCase(projectId, lossDate)

}