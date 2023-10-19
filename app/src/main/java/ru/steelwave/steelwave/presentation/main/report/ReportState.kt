package ru.steelwave.steelwave.presentation.main.report

import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.entity.user.TaskModel
import ru.steelwave.steelwave.domain.entity.user.UserModel

sealed class ReportState{


    class TaskList(val taskList: List<TaskModel>): ReportState()
    class UserItem(val userItem: UserModel): ReportState()
    class ProjectItem(val projectItem: ProjectModel): ReportState()

}
