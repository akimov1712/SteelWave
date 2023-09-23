package ru.steelwave.steelwave.data.repository.traffic

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.steelwave.steelwave.data.database.dao.traffic.TransferDao
import ru.steelwave.steelwave.data.mapper.traffic.TransferMapper
import ru.steelwave.steelwave.data.mapper.traffic.VisitionMapper
import ru.steelwave.steelwave.domain.entity.traffic.TransferModel
import ru.steelwave.steelwave.domain.repository.traffic.TransferRepository
import java.util.Date
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
    private val mapper: TransferMapper,
    private val dao: TransferDao
): TransferRepository {

    override fun getTransferListUseCase(
        projectId: Int,
        startDate: Date,
        endDate: Date
    ): LiveData<List<TransferModel>> {
        return Transformations.map(dao.getTransferList(projectId, startDate.time, endDate.time)){
            mapper.mapListDbModelToListEntity(it)
        }
    }
}