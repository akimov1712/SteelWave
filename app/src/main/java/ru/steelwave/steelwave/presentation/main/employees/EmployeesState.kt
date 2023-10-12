package ru.steelwave.steelwave.presentation.main.employees

import ru.steelwave.steelwave.domain.entity.project.ProjectModel

sealed class EmployeesState {

    object ErrorInputFirstName : EmployeesState()
    object ErrorInputLastName : EmployeesState()
    object ErrorInputMiddleName : EmployeesState()
    object ErrorInputAvatar : EmployeesState()

    object ValidationPersonalDateSuccessfully : EmployeesState()

    data class ProjectItem(val projectItem: ProjectModel): EmployeesState()

}
