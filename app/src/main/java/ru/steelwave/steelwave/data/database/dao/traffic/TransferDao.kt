package ru.steelwave.steelwave.data.database.dao.traffic

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.steelwave.steelwave.data.database.model.traffic.TransferDbModel
import ru.steelwave.steelwave.domain.entity.traffic.TransferModel
import java.util.Date


@Dao
interface TransferDao {

    @Query("SELECT * FROM transfers WHERE projectId = :projectId AND date >= :startDate AND date <= :endDate")
    fun getTransferList(
        projectId: Int,
        startDate: Long,
        endDate: Long
    ): LiveData<List<TransferDbModel>>

}