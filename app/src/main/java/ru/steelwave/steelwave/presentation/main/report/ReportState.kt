package ru.steelwave.steelwave.presentation.main.report

import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.entity.user.TaskModel
import ru.steelwave.steelwave.domain.entity.user.UserModel

sealed class ReportState{

    data object Loading: ReportState()
    data class TaskList(val taskList: List<TaskModel>): ReportState()
    data class UserItem(val userItem: UserModel): ReportState()
    data class ProjectItem(val projectItem: ProjectModel): ReportState()

}
