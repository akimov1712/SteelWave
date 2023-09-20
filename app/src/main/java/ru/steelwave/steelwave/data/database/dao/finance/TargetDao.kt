package ru.steelwave.steelwave.data.database.dao.finance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.steelwave.steelwave.data.database.model.finance.TargetDbModel

@Dao
interface TargetDao {


    @Query("SELECT * FROM targets WHERE projectId=:projectId")
    fun getAllTarget(projectId: Int): LiveData<List<TargetDbModel>>

    @Query("SELECT * FROM targets WHERE id=:targetId LIMIT 1")
    suspend fun getTargetItem(targetId: Int): TargetDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTarget(target: TargetDbModel)

    @Query("DELETE FROM targets WHERE id =:targetId")
    suspend fun deleteTarget(targetId: Int)

}