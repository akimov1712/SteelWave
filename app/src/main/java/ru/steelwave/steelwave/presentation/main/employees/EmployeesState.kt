package ru.steelwave.steelwave.presentation.main.employees

import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.entity.user.UserModel

sealed class EmployeesState {

    object ErrorInputFirstName : EmployeesState()
    object ErrorInputLastName : EmployeesState()
    object ErrorInputMiddleName : EmployeesState()
    object ErrorInputAvatar : EmployeesState()
    object ErrorInputPosition : EmployeesState()
    object ErrorInputSalary : EmployeesState()
    object ErrorInputLogin : EmployeesState()
    object ErrorInputPassword : EmployeesState()
    object ErrorInputSecretWord : EmployeesState()

    object ErrorEmployeesList : EmployeesState()

    object ValidationPersonalDateSuccessfully : EmployeesState()
    object ValidationPositionSuccessfully : EmployeesState()
    object ShouldCloseModal : EmployeesState()

    data class ProjectItem(val projectItem: ProjectModel): EmployeesState()
    data class UserList(val userList: List<UserModel>): EmployeesState()
    data class CountUsers(val countUsers: Int): EmployeesState()

}
