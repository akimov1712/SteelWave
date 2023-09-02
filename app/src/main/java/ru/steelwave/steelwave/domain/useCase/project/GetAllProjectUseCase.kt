package ru.steelwave.steelwave.domain.useCase.project

import ru.steelwave.steelwave.domain.repository.project.ProjectRepository

class GetAllProjectUseCase(private val repository: ProjectRepository) {

    operator fun invoke() = repository.getAllProjectUseCase()

}