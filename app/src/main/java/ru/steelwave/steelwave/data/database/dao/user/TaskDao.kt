package ru.steelwave.steelwave.data.database.dao.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.steelwave.steelwave.data.database.model.user.TaskDbModel
import ru.steelwave.steelwave.data.database.model.user.UserDbModel
import ru.steelwave.steelwave.domain.entity.user.TaskModel
import ru.steelwave.steelwave.domain.entity.user.UserModel

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE projectId=:projectId AND userId=:userId")
    fun getTaskList(projectId: Int, userId: Int): LiveData<List<TaskDbModel>>

    @Query("SELECT * FROM tasks WHERE id=:taskId LIMIT 1")
    suspend fun getTask(taskId: Int): TaskDbModel

    @Query("DELETE FROM tasks WHERE id=:taskId")
    suspend fun deleteTask(taskId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: TaskDbModel)

}