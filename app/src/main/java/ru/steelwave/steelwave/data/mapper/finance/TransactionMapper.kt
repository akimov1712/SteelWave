package ru.steelwave.steelwave.data.mapper.finance

import ru.steelwave.steelwave.data.database.model.finance.TransactionDbModel
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import java.sql.Date
import javax.inject.Inject

class TransactionMapper @Inject constructor() {

     fun mapEntityToDbModel(transaction: TransactionModel) = TransactionDbModel(
        id = transaction.id,
        projectId = transaction.projectId,
        date = transaction.date.time,
        isIncome = transaction.isIncome,
        name = transaction.name,
        count = transaction.count
    )

    private fun mapDbModelToEntity(transaction: TransactionDbModel) = TransactionModel(
        id = transaction.id,
        projectId = transaction.projectId,
        date = Date(transaction.date),
        isIncome = transaction.isIncome,
        name = transaction.name,
        count = transaction.count
    )

    fun mapListDbModelToListEntity(transactionList: List<TransactionDbModel>) = transactionList.map {
            mapDbModelToEntity(it)
        }

}