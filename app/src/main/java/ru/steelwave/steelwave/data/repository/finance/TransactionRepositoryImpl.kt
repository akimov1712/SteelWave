package ru.steelwave.steelwave.data.repository.finance

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.steelwave.steelwave.Loger
import ru.steelwave.steelwave.data.database.dao.finance.TransactionDao
import ru.steelwave.steelwave.data.mapper.finance.TransactionMapper
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import ru.steelwave.steelwave.domain.repository.finance.TransactionRepository
import java.sql.Date
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val mapper: TransactionMapper,
    private val dao: TransactionDao
): TransactionRepository {

    override fun getTransactionList(
    ): LiveData<List<TransactionModel>> {
        return Transformations.map(dao.getTransactionList()){
            mapper.mapListDbModelToListEntity(it)
        }
    }

    override suspend fun addTransaction(transaction: TransactionModel) {
        dao.addTransaction(mapper.mapEntityToDbModel(transaction))
    }
}