package ru.steelwave.steelwave.data.database.dao.finance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.steelwave.steelwave.data.database.model.finance.IncomeDbModel

@Dao
interface IncomeDao {
    @Query("SELECT * FROM incomes WHERE projectId=:projectId and date=:date LIMIT 1")
    suspend fun getIncome(projectId: Int, date: Long): IncomeDbModel


}