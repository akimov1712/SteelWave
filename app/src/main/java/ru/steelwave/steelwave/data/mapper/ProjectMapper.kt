package ru.steelwave.steelwave.data.mapper

import ru.steelwave.steelwave.data.database.model.ProjectDbModel
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import javax.inject.Inject

class ProjectMapper @Inject constructor() {

    fun mapEntityToDbModel(project: ProjectModel) =
        ProjectDbModel(
            id = project.id,
            name = project.name,
            previewImage = project.previewImage,
            dateRelease = project.dateRelease,
            trafic = project.trafic,
            income = project.income,
        )

    fun mapDbModelToEntity(project: ProjectDbModel) =
        ProjectModel(
            id = project.id,
            name = project.name,
            previewImage = project.previewImage,
            dateRelease = project.dateRelease,
            trafic = project.trafic,
            income = project.income,
        )

    fun mapListDbModelToListEntity(projectList: List<ProjectDbModel>) = projectList.map {
        mapDbModelToEntity(it)
    }

}