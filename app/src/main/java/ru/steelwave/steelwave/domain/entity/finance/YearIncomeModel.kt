package ru.steelwave.steelwave.domain.entity.finance

data class YearIncomeModel(
    val id: Int = UNDEFINED_ID,
    val projectId: Int,
    val year: Int,
    val yearIncomeList: List<TransactionModel> = listOf(
        TransactionModel("Январь", 534543),
        TransactionModel("Февраль", 2324),
        TransactionModel("Март", 9879),
        TransactionModel("Апрель", 34),
        TransactionModel("Май", 0),
        TransactionModel("Июнь", 3523),
        TransactionModel("Июль", 1111),
        TransactionModel("Август", 5646),
        TransactionModel("Сентябрь", 0),
        TransactionModel("Октябрь", 12),
        TransactionModel("Ноябрь", 5555),
        TransactionModel("Декабрь", 12),
    )
){
    companion object{
        private const val UNDEFINED_ID = 0
    }
}
