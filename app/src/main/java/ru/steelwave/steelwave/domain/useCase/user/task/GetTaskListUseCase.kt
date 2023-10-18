package ru.steelwave.steelwave.domain.useCase.user.task

import ru.steelwave.steelwave.domain.repository.user.TaskRepository
import javax.inject.Inject

class GetTaskListUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    operator fun invoke(projectId: Int) = repository.getTaskListUseCase(projectId)

}