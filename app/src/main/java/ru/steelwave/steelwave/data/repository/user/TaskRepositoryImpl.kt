package ru.steelwave.steelwave.data.repository.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.steelwave.steelwave.Const
import ru.steelwave.steelwave.data.database.dao.user.TaskDao
import ru.steelwave.steelwave.data.database.dao.user.UserDao
import ru.steelwave.steelwave.data.mapper.user.TaskMapper
import ru.steelwave.steelwave.data.mapper.user.UserMapper
import ru.steelwave.steelwave.domain.entity.user.TaskModel
import ru.steelwave.steelwave.domain.entity.user.UserModel
import ru.steelwave.steelwave.domain.repository.user.TaskRepository
import ru.steelwave.steelwave.domain.repository.user.UserRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val mapper: TaskMapper,
    private val dao: TaskDao
): TaskRepository {

    override fun getTaskListUseCase(projectId: Int, userId: Int): LiveData<List<TaskModel>> =
        Transformations.map(dao.getTaskList(projectId, userId)){
            mapper.mapListDbModelToListEntity(it)
        }
    

    override suspend fun getTaskUseCase(taskId: Int) = mapper.mapDbModelToEntity(dao.getTask(taskId))

    override suspend fun addTaskUseCase(task: TaskModel) {
        dao.addTask(mapper.mapEntityToDbModel(task))
    }

    override suspend fun deleteTaskUseCase(taskId: Int) {
        dao.deleteTask(taskId)
    }
}