package ru.steelwave.steelwave.domain.useCase.finance.income

import ru.steelwave.steelwave.domain.repository.finance.IncomeRepository
import java.sql.Date

class GetIncomeUseCase(private val repository: IncomeRepository) {

    suspend operator fun invoke(incomeDate: Date) = repository.getIncomeUseCase(incomeDate)

}