package ru.steelwave.steelwave.data.mapper.finance

import ru.steelwave.steelwave.data.database.model.finance.YearIncomeDbModel
import ru.steelwave.steelwave.domain.entity.finance.YearIncomeModel
import javax.inject.Inject

class YearIncomeMapper @Inject constructor(
    private val mapper: TransactionMapper
){

    fun mapEntityToDbModel(yearIncome: YearIncomeModel) = YearIncomeDbModel(
        id = yearIncome.id,
        projectId = yearIncome.projectId,
        year = yearIncome.year,
        yearIncomeList = mapper.mapListEntityToListDbModel(yearIncome.yearIncomeList)
    )

    fun mapDbModelToEntity(yearIncome: YearIncomeDbModel) = YearIncomeModel(
        id = yearIncome.id,
        projectId = yearIncome.projectId,
        year = yearIncome.year,
        yearIncomeList = mapper.mapListDbModelToListEntity(yearIncome.yearIncomeList)
    )

}