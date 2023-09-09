package ru.steelwave.steelwave.domain.repository.finance

import ru.steelwave.steelwave.domain.entity.finance.YearIncomeModel

interface IncomeYearRepository {

    suspend fun getIncomeUseCase(incomeYear: Int): YearIncomeModel

}