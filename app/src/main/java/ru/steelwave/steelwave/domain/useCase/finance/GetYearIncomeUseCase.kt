package ru.steelwave.steelwave.domain.useCase.finance

import ru.steelwave.steelwave.domain.repository.finance.YearIncomeRepository
import javax.inject.Inject

class GetYearIncomeUseCase @Inject constructor(private val repository: YearIncomeRepository) {

    suspend operator fun invoke(incomeYear: Int, year: Int) = repository.getYearIncomeUseCase(incomeYear, year)

}