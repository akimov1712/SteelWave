package ru.steelwave.steelwave.domain.useCase.user.task

import ru.steelwave.steelwave.domain.entity.user.TaskModel
import ru.steelwave.steelwave.domain.repository.user.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(taskId: Int) {
        repository.deleteTaskUseCase(taskId)
    }

}