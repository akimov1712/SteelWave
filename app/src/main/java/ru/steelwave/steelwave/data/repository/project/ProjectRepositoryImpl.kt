package ru.steelwave.steelwave.data.repository.project

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.steelwave.steelwave.data.database.AppDatabase
import ru.steelwave.steelwave.data.database.dao.ProjectDao
import ru.steelwave.steelwave.data.mapper.ProjectMapper
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.repository.project.ProjectRepository
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val dao: ProjectDao,
    private val mapper: ProjectMapper
): ProjectRepository {


    override suspend fun addProjectUseCase(project: ProjectModel) {
        dao.insertProject(mapper.mapEntityToDbModel(project))
    }

    override suspend fun deleteProjectUseCase(project: ProjectModel) {
        dao.deleteProject(mapper.mapEntityToDbModel(project))
    }

    override suspend fun getProjectUseCase(projectId: Int): ProjectModel {
        return mapper.mapDbModelToEntity(dao.getProject(projectId))
    }

    override fun getAllProjectUseCase(): LiveData<List<ProjectModel>> {
        return Transformations.map(dao.getAllProjects()){
            mapper.mapListDbModelToListEntity(it)
        }
    }
}