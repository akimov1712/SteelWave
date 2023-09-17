package ru.steelwave.steelwave.data.database.dao.finance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.steelwave.steelwave.data.database.model.finance.LossDbModel

@Dao
interface LossDao {

    @Query("SELECT * FROM losses WHERE projectId = :projectId AND date = :date LIMIT 1")
    suspend fun getLoss(projectId: Int, date: Long): LossDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLoss(loss: LossDbModel)

}