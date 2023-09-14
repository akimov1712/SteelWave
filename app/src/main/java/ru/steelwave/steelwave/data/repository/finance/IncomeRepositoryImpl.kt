package ru.steelwave.steelwave.data.repository.finance

import ru.steelwave.steelwave.data.database.dao.finance.IncomeDao
import ru.steelwave.steelwave.data.mapper.finance.IncomeMapper
import ru.steelwave.steelwave.domain.entity.finance.IncomeModel
import ru.steelwave.steelwave.domain.repository.finance.IncomeRepository
import java.sql.Date
import javax.inject.Inject

class IncomeRepositoryImpl @Inject constructor(
    private val dao: IncomeDao,
    private val mapper: IncomeMapper
) : IncomeRepository {

    override suspend fun getIncomeUseCase(projectId: Int,incomeDate: Date): IncomeModel {
        val dateLong = incomeDate.time
        val income = dao.getIncome(projectId, dateLong)
        return mapper.mapDbModelToEntity(income)
    }

}