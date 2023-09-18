package ru.steelwave.steelwave.domain.useCase.finance.transaction

import ru.steelwave.steelwave.domain.repository.finance.TransactionRepository
import java.sql.Date
import javax.inject.Inject

class GetTransactionListUseCase @Inject constructor(
    private val repository: TransactionRepository
){

    operator fun invoke() =
        repository.getTransactionList()

}