package ru.steelwave.steelwave.domain.useCase.finance.transaction

import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import ru.steelwave.steelwave.domain.repository.finance.TransactionRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transaction: TransactionModel){
        repository.addTransaction(transaction)
    }

}