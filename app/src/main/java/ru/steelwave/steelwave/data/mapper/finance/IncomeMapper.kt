package ru.steelwave.steelwave.data.mapper.finance

import ru.steelwave.steelwave.data.database.model.finance.IncomeDbModel
import ru.steelwave.steelwave.domain.entity.finance.IncomeModel
import javax.inject.Inject

class IncomeMapper @Inject constructor(
    private val mapper: TransactionMapper
){

    fun mapEntityToDbModel(income: IncomeModel) = IncomeDbModel(
        id = income.id,
        projectId = income.projectId,
        date = income.date,
        projectProfit = income.projectProfit,
        detailedIncome = mapper.mapListEntityToListDbModel(income.detailedIncome)
    )

    fun mapDbModelToEntity(income: IncomeDbModel) = IncomeModel(
        id = income.id,
        projectId = income.projectId,
        date = income.date,
        projectProfit = income.projectProfit,
        detailedIncome = mapper.mapListDbModelToListEntity(income.detailedIncome)
    )

}