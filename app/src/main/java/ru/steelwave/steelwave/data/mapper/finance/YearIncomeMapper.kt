package ru.steelwave.steelwave.data.mapper.finance

import ru.steelwave.steelwave.data.database.model.finance.YearIncomeDbModel
import ru.steelwave.steelwave.domain.entity.finance.YearIncomeModel
import javax.inject.Inject

class YearIncomeMapper @Inject constructor(
    private val mapper: TransactionMapper
){

    fun mapEntityToDbModel(yearIncome: YearIncomeModel?) = yearIncome?.let {
        YearIncomeDbModel(
            id = yearIncome.id,
            projectId = yearIncome.projectId,
            year = yearIncome.year,
        )
    }



    fun mapDbModelToEntity(yearIncome: YearIncomeDbModel?) = yearIncome?.let {
        YearIncomeModel(
            id = yearIncome.id,
            projectId = yearIncome.projectId,
            year = yearIncome.year,
        )
    }



}