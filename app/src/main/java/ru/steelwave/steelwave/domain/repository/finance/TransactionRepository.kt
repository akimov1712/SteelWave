package ru.steelwave.steelwave.domain.repository.finance

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import java.sql.Date

interface TransactionRepository {

    fun getTransactionList(projectId: Int, date: Date): Flow<List<TransactionModel>>
    suspend fun addTransaction(transaction: TransactionModel)

}