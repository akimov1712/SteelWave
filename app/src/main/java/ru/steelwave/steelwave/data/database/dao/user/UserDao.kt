package ru.steelwave.steelwave.data.database.dao.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.steelwave.steelwave.data.database.model.user.UserDbModel
import ru.steelwave.steelwave.domain.entity.user.UserModel

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE projectId = :projectId")
    fun getUserList(projectId: Int): LiveData<List<UserDbModel>>

    @Query("SELECT * FROM users WHERE id=:userId LIMIT 1")
    suspend fun getUser(userId: Int): UserDbModel

    @Delete
    suspend fun deleteUser(user: UserDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserDbModel)

}