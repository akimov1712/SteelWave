package ru.steelwave.steelwave.domain.repository.finance

import ru.steelwave.steelwave.domain.entity.finance.IncomeModel
import ru.steelwave.steelwave.domain.entity.finance.IncomeYearModel
import java.sql.Date

interface IncomeYearRepository {

    suspend fun getIncomeUseCase(incomeYear: Int): IncomeYearModel

}