package ru.steelwave.steelwave.data.mapper.finance

import ru.steelwave.steelwave.data.database.model.finance.IncomeDbModel
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
        detailedIncome = mapper.mapListEntityToListDbModel(loss.detailedIncome)
    )

    fun mapDbModelToEntity(loss: LossDbModel) = LossModel(
        id = loss.id,
        projectId = loss.projectId,
        date = loss.date,
        detailedIncome = mapper.mapListDbModelToListEntity(loss.detailedIncome)
    )

}