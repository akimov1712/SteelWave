package ru.steelwave.steelwave.domain.useCase.project

import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.repository.project.ProjectRepository

class DeleteProjectUseCase(private val repository: ProjectRepository) {

    suspend operator fun invoke(user: ProjectModel){
        repository.deleteProjectUseCase(user)
    }

}