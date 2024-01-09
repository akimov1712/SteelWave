package ru.steelwave.steelwave.data.database.dao.finance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.steelwave.steelwave.data.database.model.finance.TargetDbModel
import ru.steelwave.steelwave.data.database.model.finance.TransactionDbModel
import java.sql.Date

@Dao
interface TransactionDao {


    @Query("SELECT * FROM transactions WHERE projectId=:projectId AND date=:date")
    fun getTransactionList(projectId: Int, date: Long): Flow<List<TransactionDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transaction: TransactionDbModel)

    @Query("DELETE FROM transactions WHERE projectId=:projectId")
    suspend fun deleteTransaction(projectId: Int)

}