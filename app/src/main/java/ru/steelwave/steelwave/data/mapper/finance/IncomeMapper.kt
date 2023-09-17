package ru.steelwave.steelwave.data.mapper.finance

import ru.steelwave.steelwave.data.database.model.finance.IncomeDbModel
import ru.steelwave.steelwave.domain.entity.finance.IncomeModel
import javax.inject.Inject

class IncomeMapper @Inject constructor(
    private val mapper: TransactionMapper
){

    fun mapEntityToDbModel(income: IncomeModel): IncomeDbModel{
        return IncomeDbModel(
            id = income.id,
            projectId = income.projectId,
            date = income.date,
            projectProfit = income.projectProfit,
            transactionList = mapper.mapListEntityToListDbModel(income.transactionList)
        )
    }

    fun mapDbModelToEntity(income: IncomeDbModel?) = income?.let {
        IncomeModel(
            id = income.id,
            projectId = income.projectId,
            date = income.date,
            projectProfit = income.projectProfit,
            transactionList = mapper.mapListDbModelToListEntity(income.transactionList)
        )
    }?: null

}