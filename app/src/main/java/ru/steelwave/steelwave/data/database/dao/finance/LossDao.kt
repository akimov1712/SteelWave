package ru.steelwave.steelwave.data.database.dao.finance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.steelwave.steelwave.data.database.model.finance.IncomeDbModel
import ru.steelwave.steelwave.data.database.model.finance.LossDbModel

@Dao
interface LossDao {
    @Query("SELECT * FROM losses WHERE projectId=:projectId AND date=:date")
    fun getLoss(projectId: Int, date: Long): LossDbModel

    @Insert
    fun addLoss(loss: LossDbModel)

}