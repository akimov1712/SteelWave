package ru.steelwave.steelwave.data.mapper.traffic

import ru.steelwave.steelwave.data.database.model.traffic.TransferDbModel
import ru.steelwave.steelwave.domain.entity.traffic.TransferModel
import java.util.Date
import javax.inject.Inject

class TransferMapper @Inject constructor() {

    fun mapEntityToDbModel(transfer: TransferModel) = TransferDbModel(
        id = transfer.id,
        projectId = transfer.projectId,
        name = transfer.name,
        count = transfer.count,
        startDate = transfer.startDate.time,
        endDate = transfer.endDate.time
    )

    fun mapDbModelToEntity(transfer: TransferDbModel) = TransferModel(
        id = transfer.id,
        projectId = transfer.projectId,
        name = transfer.name,
        count = transfer.count,
        startDate = Date(transfer.startDate),
        endDate = Date(transfer.endDate)
    )

    fun mapListDbModelToListEntity(transferList: List<TransferDbModel>) = transferList.map{
        mapDbModelToEntity(it)
    }

    fun mapListEntityToListDbModel(transferList: List<TransferModel>) = transferList.map{
        mapEntityToDbModel(it)
    }

}