package ru.steelwave.steelwave.domain.repository.traffic

import androidx.lifecycle.LiveData
import ru.steelwave.steelwave.domain.entity.traffic.TransferModel
import java.util.Date

interface TransferRepository {

    fun getTransferListUseCase(projectId: Int, startDate: Date, endDate: Date): LiveData<List<TransferModel>>

}