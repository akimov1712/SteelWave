package ru.steelwave.steelwave.presentation.main.project

import ru.steelwave.steelwave.domain.entity.project.ProjectModel


sealed class ProjectState{

    data object ErrorInputName : ProjectState()
    data object ErrorImage : ProjectState()
    data object ShouldCloseScreen : ProjectState()
    data object Loading : ProjectState()
    data class ProjectList(val projectList: List<ProjectModel>): ProjectState()
    data class ProjectItem(val item: ProjectModel): ProjectState()

}
