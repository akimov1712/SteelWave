package ru.steelwave.steelwave.domain.repository.user

import androidx.lifecycle.LiveData
import ru.steelwave.steelwave.domain.entity.user.TaskModel

interface TaskRepository {

    fun getTaskListUseCase(projectId: Int): LiveData<List<TaskModel>>
    suspend fun getTaskUseCase(taskId: Int): TaskModel
    suspend fun addTaskUseCase(task: TaskModel)
    suspend fun deleteTaskUseCase(taskId: Int)

}