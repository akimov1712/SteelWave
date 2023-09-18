package ru.steelwave.steelwave.data.database.dao.finance

import androidx.room.Dao
import androidx.room.Query
import ru.steelwave.steelwave.data.database.model.finance.YearIncomeDbModel

@Dao
interface YearIncomeDao {
    @Query("SELECT * FROM yearIncomes WHERE projectId=:projectId AND year=:year LIMIT 1")
    suspend fun getYearIncome(projectId: Int, year: Int): YearIncomeDbModel

}