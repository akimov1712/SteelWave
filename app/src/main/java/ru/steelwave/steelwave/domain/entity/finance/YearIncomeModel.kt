package ru.steelwave.steelwave.domain.entity.finance

data class YearIncomeModel(
    val id: Int = UNDEFINED_ID,
    val projectId: Int,
    val year: Int,
    var january: Int = 0,
    var february: Int = 0,
    var marth: Int = 0,
    var april: Int = 0,
    var may: Int = 0,
    var june: Int = 0,
    var jule: Int = 0,
    var august: Int = 0,
    var september: Int = 0,
    var octomber: Int = 0,
    var november: Int = 0,
    var december: Int = 0,
){
    companion object{
        private const val UNDEFINED_ID = 0
    }
}
