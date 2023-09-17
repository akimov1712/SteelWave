package ru.steelwave.steelwave.data.repository.finance

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.steelwave.steelwave.data.database.dao.finance.YearIncomeDao
import ru.steelwave.steelwave.data.mapper.finance.YearIncomeMapper
import ru.steelwave.steelwave.domain.entity.finance.YearIncomeModel
import ru.steelwave.steelwave.domain.repository.finance.YearIncomeRepository
import javax.inject.Inject

class YearIncomeRepositoryImpl @Inject constructor(
    private val dao: YearIncomeDao,
    private val mapper: YearIncomeMapper
): YearIncomeRepository{

    override suspend fun getYearIncomeUseCase(projectId: Int, year: Int): YearIncomeModel?{
        return mapper.mapDbModelToEntity(dao.getYearIncome(projectId, year))
    }
}