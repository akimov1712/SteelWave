package ru.steelwave.steelwave.data.database.dao.traffic

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.steelwave.steelwave.data.database.model.traffic.VisitionDbModel
import ru.steelwave.steelwave.domain.entity.traffic.TransferModel
import ru.steelwave.steelwave.domain.entity.traffic.VisitionModel
import java.util.Date

@Dao
interface VisitionDao {

    @Query("SELECT * FROM visitions WHERE projectId = :projectId AND date = :date LIMIT 1")
    fun getVisition(projectId: Int, date: Long): Flow<VisitionDbModel>

}