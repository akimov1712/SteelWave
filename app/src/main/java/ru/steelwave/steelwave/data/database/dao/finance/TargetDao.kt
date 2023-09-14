package ru.steelwave.steelwave.data.database.dao.finance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.steelwave.steelwave.data.database.model.finance.TargetDbModel

@Dao
interface TargetDao {


    @Query("SELECT * FROM targets WHERE projectId=:projectId")
    fun getAllTarget(projectId: Int): LiveData<List<TargetDbModel>>
    @Insert
    fun addTarget(target: TargetDbModel)

}