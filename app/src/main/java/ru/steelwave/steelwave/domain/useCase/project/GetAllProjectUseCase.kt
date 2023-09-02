package ru.steelwave.steelwave.domain.useCase.project

import ru.steelwave.steelwave.domain.repository.project.ProjectRepository
import javax.inject.Inject

class GetAllProjectUseCase @Inject constructor(private val repository: ProjectRepository) {

    operator fun invoke() = repository.getAllProjectUseCase()

}