package ru.steelwave.steelwave.data.mapper.finance

import ru.steelwave.steelwave.data.database.model.finance.LossDbModel
import ru.steelwave.steelwave.domain.entity.finance.LossModel
import javax.inject.Inject

class LossMapper @Inject constructor(
    private val mapper: TransactionMapper
){

    fun mapEntityToDbModel(loss: LossModel) = LossDbModel(
        id = loss.id,
        projectId = loss.projectId,
        date = loss.date,
        transactionList = mapper.mapListEntityToListDbModel(loss.transactionList)
    )

    fun mapDbModelToEntity(loss: LossDbModel?) = loss?.let{
        LossModel(
            id = loss.id,
            projectId = loss.projectId,
            date = loss.date,
            transactionList = mapper.mapListDbModelToListEntity(loss.transactionList)
        )
    }?: null
}