package ru.steelwave.steelwave.domain.useCase.finance.income

import ru.steelwave.steelwave.domain.repository.finance.IncomeRepository
import java.sql.Date
import javax.inject.Inject

class GetIncomeUseCase @Inject constructor(private val repository: IncomeRepository) {

    suspend operator fun invoke(incomeDate: Date) = repository.getIncomeUseCase(incomeDate)

}