package ru.steelwave.steelwave.presentation.main.employees

import ru.steelwave.steelwave.domain.entity.project.ProjectModel

sealed class EmployeesState {

    object ErrorInputFirstName : EmployeesState()
    object ErrorInputLastName : EmployeesState()
    object ErrorInputMiddleName : EmployeesState()
    object ErrorInputAvatar : EmployeesState()
    object ErrorInputPosition : EmployeesState()
    object ErrorInputProject : EmployeesState()
    object ErrorInputSalary : EmployeesState()
    object ErrorInputLogin : EmployeesState()
    object ErrorInputPassword : EmployeesState()
    object ErrorInputSecretWord : EmployeesState()
    object ValidationPersonalDateSuccessfully : EmployeesState()
    object ValidationPositionSuccessfully : EmployeesState()
    object ShouldCloseModal : EmployeesState()

    data class ProjectItem(val projectItem: ProjectModel): EmployeesState()

}
