package ru.steelwave.steelwave.domain.repository.finance

import androidx.lifecycle.LiveData
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import java.sql.Date

interface TransactionRepository {

    fun getTransactionList(projectId: Int, date: Date): LiveData<List<TransactionModel>>
    suspend fun addTransaction(transaction: TransactionModel)

}