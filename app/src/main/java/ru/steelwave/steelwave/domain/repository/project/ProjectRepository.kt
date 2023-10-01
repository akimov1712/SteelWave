package ru.steelwave.steelwave.domain.repository.project

import androidx.lifecycle.LiveData
import ru.steelwave.steelwave.domain.entity.project.ProjectModel

interface ProjectRepository {

    suspend fun addProjectUseCase(project: ProjectModel)
    suspend fun deleteProjectUseCase(projectId: Int)
    suspend fun getProjectUseCase(projectId: Int): ProjectModel
    fun getAllProjectUseCase(): LiveData<List<ProjectModel>>

}