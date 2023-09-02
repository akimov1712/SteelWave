package ru.steelwave.steelwave.domain.useCase.project

import ru.steelwave.steelwave.domain.repository.project.ProjectRepository

class GetProjectUseCase(private val repository: ProjectRepository) {

    suspend operator fun invoke(userId: Int) = repository.getProjectUseCase(userId)

}