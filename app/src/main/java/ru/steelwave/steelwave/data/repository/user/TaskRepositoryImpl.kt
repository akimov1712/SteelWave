package ru.steelwave.steelwave.data.repository.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.steelwave.steelwave.data.database.dao.user.TaskDao
import ru.steelwave.steelwave.data.mapper.user.TaskMapper
import ru.steelwave.steelwave.domain.entity.user.TaskModel
import ru.steelwave.steelwave.domain.repository.user.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val mapper: TaskMapper,
    private val dao: TaskDao
) : TaskRepository {

    override fun getTaskListUseCase(projectId: Int, userId: Int): Flow<List<TaskModel>> =
        dao.getTaskList(projectId, userId).map {
            mapper.mapListDbModelToListEntity(it)
        }


    override suspend fun getTaskUseCase(taskId: Int) =
        mapper.mapDbModelToEntity(dao.getTask(taskId))

    override suspend fun addTaskUseCase(task: TaskModel) {
        dao.addTask(mapper.mapEntityToDbModel(task))
    }

    override suspend fun deleteTaskUseCase(taskId: Int) {
        dao.deleteTask(taskId)
    }
}