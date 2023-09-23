package ru.steelwave.steelwave.domain.useCase.finance

import ru.steelwave.steelwave.domain.repository.finance.TransactionRepository
import java.sql.Date
import javax.inject.Inject

class GetTransactionListUseCase @Inject constructor(
    private val repository: TransactionRepository
){

    operator fun invoke(projectId: Int, date: Date) =
        repository.getTransactionList(projectId, date)

}