package ru.steelwave.steelwave.data.repository.traffic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.steelwave.steelwave.data.database.dao.traffic.TransferDao
import ru.steelwave.steelwave.data.mapper.traffic.TransferMapper
import ru.steelwave.steelwave.domain.entity.traffic.TransferModel
import ru.steelwave.steelwave.domain.repository.traffic.TransferRepository
import java.util.Date
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
    private val mapper: TransferMapper,
    private val dao: TransferDao
) : TransferRepository {

    override fun getTransferListUseCase(
        projectId: Int,
        startDate: Date,
        endDate: Date
    ): Flow<List<TransferModel>> {
        return dao.getTransferList(projectId, startDate.time, endDate.time).map {
            mapper.mapListDbModelToListEntity(it)
        }
    }
}