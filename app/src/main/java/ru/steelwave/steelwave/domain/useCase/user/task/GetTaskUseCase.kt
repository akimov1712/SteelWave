package ru.steelwave.steelwave.domain.useCase.user.task

import ru.steelwave.steelwave.domain.repository.user.TaskRepository
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(taskId: Int) = repository.getTaskUseCase(taskId)

}