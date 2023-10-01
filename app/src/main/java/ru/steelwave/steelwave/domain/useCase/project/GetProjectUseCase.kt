package ru.steelwave.steelwave.domain.useCase.project

import ru.steelwave.steelwave.domain.repository.project.ProjectRepository
import javax.inject.Inject

class GetProjectUseCase @Inject constructor(private val repository: ProjectRepository) {

    suspend operator fun invoke(projectId: Int) = repository.getProjectUseCase(projectId)

}