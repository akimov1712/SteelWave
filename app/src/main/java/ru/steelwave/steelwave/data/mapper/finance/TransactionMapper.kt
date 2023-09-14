package ru.steelwave.steelwave.data.mapper.finance

import ru.steelwave.steelwave.data.database.model.finance.TransactionDbModel
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import javax.inject.Inject

class TransactionMapper @Inject constructor() {

    private fun mapEntityToDbModel(transaction: TransactionModel) = TransactionDbModel(
        name = transaction.name,
        count = transaction.count
    )

    private fun mapDbModelToEntity(transaction: TransactionDbModel) = TransactionModel(
        name = transaction.name,
        count = transaction.count
    )

    fun mapListEntityToListDbModel(transactionList: List<TransactionModel>) = transactionList.map {
        mapEntityToDbModel(it)
    }

    fun mapListDbModelToListEntity(transactionList: List<TransactionDbModel>) = transactionList.map {
        mapDbModelToEntity(it)
    }

}