package ru.steelwave.steelwave.data.repository.finance

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.steelwave.steelwave.data.database.dao.finance.TransactionDao
import ru.steelwave.steelwave.data.mapper.finance.TransactionMapper
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import ru.steelwave.steelwave.domain.repository.finance.TransactionRepository
import java.sql.Date
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val mapper: TransactionMapper,
    private val dao: TransactionDao
) : TransactionRepository {

    override fun getTransactionList(
        projectId: Int,
        date: Date
    ): Flow<List<TransactionModel>> {
        return dao.getTransactionList(projectId, date.time).map {
            mapper.mapListDbModelToListEntity(it)
        }
    }

    override suspend fun addTransaction(transaction: TransactionModel) {
        dao.addTransaction(mapper.mapEntityToDbModel(transaction))
    }
}