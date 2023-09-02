package ru.steelwave.steelwave.domain.useCase.finance.incomeYear

import ru.steelwave.steelwave.domain.repository.finance.IncomeYearRepository
import javax.inject.Inject

class GetIncomeYearUseCase @Inject constructor(private val repository: IncomeYearRepository) {

    suspend operator fun invoke(incomeYear: Int) = repository.getIncomeUseCase(incomeYear)

}