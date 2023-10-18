package ru.steelwave.steelwave.data.mapper.user

import ru.steelwave.steelwave.data.database.model.user.TaskDbModel
import ru.steelwave.steelwave.domain.entity.user.TaskModel
import javax.inject.Inject

class TaskMapper @Inject constructor() {

    fun mapEntityToDbModel(task: TaskModel) = TaskDbModel(
        id = task.id,
        projectId = task.projectId,
        userId = task.userId,
        descr = task.descr,
        state = task.state
    )

    fun mapDbModelToEntity(task: TaskDbModel) = TaskModel(
        id = task.id,
        projectId = task.projectId,
        userId = task.userId,
        descr = task.descr,
        state = task.state
    )

    fun mapListDbModelToListEntity(taskList: List<TaskDbModel>) = taskList.map {
        mapDbModelToEntity(it)
    }

    fun mapListEntityToListDbModel(taskList: List<TaskModel>) = taskList.map {
        mapEntityToDbModel(it)
    }

}