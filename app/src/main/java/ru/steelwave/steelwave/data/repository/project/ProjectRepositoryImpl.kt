package ru.steelwave.steelwave.data.repository.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.steelwave.steelwave.data.database.dao.finance.TransactionDao
import ru.steelwave.steelwave.data.database.dao.project.ProjectDao
import ru.steelwave.steelwave.data.mapper.project.ProjectMapper
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.repository.project.ProjectRepository
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val projectDao: ProjectDao,
    private val transactionDao: TransactionDao,
    private val mapper: ProjectMapper
): ProjectRepository {


    override suspend fun addProjectUseCase(project: ProjectModel) {
        projectDao.insertProject(mapper.mapEntityToDbModel(project))
    }

    override suspend fun deleteProjectUseCase(project: ProjectModel) {
        projectDao.deleteProject(mapper.mapEntityToDbModel(project))
        transactionDao.deleteTransaction(project.id)
    }

    override suspend fun getProjectUseCase(projectId: Int): ProjectModel {
        return mapper.mapDbModelToEntity(projectDao.getProject(projectId))
    }

    override fun getAllProjectUseCase(): LiveData<List<ProjectModel>> {
        return Transformations.map(projectDao.getAllProjects()){
            mapper.mapListDbModelToListEntity(it)
        }
    }
}