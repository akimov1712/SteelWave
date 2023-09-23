package ru.steelwave.steelwave.domain.useCase.traffic

import ru.steelwave.steelwave.domain.repository.traffic.VisitionRepository
import java.util.Date
import javax.inject.Inject

class GetVisitionUseCase @Inject constructor(
    private val repository: VisitionRepository
) {

    operator fun invoke(projectId: Int, date: Date) = repository.getVisitionUseCase(projectId, date)

}