package ru.steelwave.steelwave.domain.repository.traffic

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ru.steelwave.steelwave.domain.entity.traffic.TransferModel
import java.util.Date

interface TransferRepository {

    fun getTransferListUseCase(projectId: Int, startDate: Date, endDate: Date): Flow<List<TransferModel>>

}