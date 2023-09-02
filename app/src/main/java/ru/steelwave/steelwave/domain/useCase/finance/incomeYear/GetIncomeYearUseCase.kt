package ru.steelwave.steelwave.domain.useCase.finance.incomeYear

import javax.inject.Inject

class GetIncomeYearUseCase @Inject constructor(private val repository: IncomeYearRepository) {

    suspend operator fun invoke(incomeYear: Int) = repository.getIncomeUseCase(incomeYear)

}