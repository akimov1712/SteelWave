package ru.steelwave.steelwave.domain.repository.finance

import ru.steelwave.steelwave.domain.entity.finance.YearIncomeModel

interface YearIncomeRepository {

    suspend fun getYearIncomeUseCase(incomeYear: Int): YearIncomeModel

}