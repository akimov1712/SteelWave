package ru.steelwave.steelwave.domain.useCase.traffic

import ru.steelwave.steelwave.domain.repository.traffic.TransferRepository
import java.util.Date
import javax.inject.Inject

class GetTransferListUseCase @Inject constructor(
    private val repository: TransferRepository
) {

    operator fun invoke(projectId: Int, startDate: Date, endDate: Date) =
        repository.getTransferListUseCase(projectId, startDate, endDate)

}