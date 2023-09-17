package ru.steelwave.steelwave.domain.entity.finance

data class LossModel(
    val id: Int = UNDEFINED_ID,
    val projectId: Int,
    val date: Long,
    var transactionList: MutableList<TransactionModel> = mutableListOf()
){
    fun addTransaction(transaction: TransactionModel){
        transactionList.add(transaction)
    }
    companion object{
        private const val UNDEFINED_ID = 0
    }
}