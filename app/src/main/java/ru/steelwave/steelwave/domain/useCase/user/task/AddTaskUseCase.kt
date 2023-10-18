package ru.steelwave.steelwave.domain.useCase.user.task

import ru.steelwave.steelwave.domain.entity.user.TaskModel
import ru.steelwave.steelwave.domain.repository.user.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: TaskModel) {
        repository.addTaskUseCase(task)
    }

}