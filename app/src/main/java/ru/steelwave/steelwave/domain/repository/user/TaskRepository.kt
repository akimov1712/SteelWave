package ru.steelwave.steelwave.domain.repository.user

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ru.steelwave.steelwave.domain.entity.user.TaskModel

interface TaskRepository {

    fun getTaskListUseCase(projectId: Int, userId: Int): Flow<List<TaskModel>>
    suspend fun getTaskUseCase(taskId: Int): TaskModel
    suspend fun addTaskUseCase(task: TaskModel)
    suspend fun deleteTaskUseCase(taskId: Int)

}