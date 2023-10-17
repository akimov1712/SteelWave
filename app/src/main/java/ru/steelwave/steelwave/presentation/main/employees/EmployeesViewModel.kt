package ru.steelwave.steelwave.presentation.main.employees

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.domain.entity.user.UserModel
import ru.steelwave.steelwave.domain.useCase.project.GetProjectUseCase
import ru.steelwave.steelwave.domain.useCase.user.AddUserUseCase
import ru.steelwave.steelwave.domain.useCase.user.DeleteUserUseCase
import ru.steelwave.steelwave.domain.useCase.user.GetCountUsersUseCase
import ru.steelwave.steelwave.domain.useCase.user.GetTotalSalaryUseCase
import ru.steelwave.steelwave.domain.useCase.user.GetUserListUseCase
import ru.steelwave.steelwave.domain.useCase.user.GetUserUseCase
import javax.inject.Inject

class EmployeesViewModel @Inject constructor(
    private val getProjectUseCase: GetProjectUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getUserListUseCase: GetUserListUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getCountUsersUseCase: GetCountUsersUseCase,
    private val getTotalSalaryUseCase: GetTotalSalaryUseCase
) : ViewModel() {

    private val _state = MutableLiveData<EmployeesState>()
    val state: LiveData<EmployeesState>
        get() = _state

    fun deleteEmployee(userId: Int){
        viewModelScope.launch {
            deleteUserUseCase(userId)
            _state.value = EmployeesState.ShouldCloseModal
        }
    }

    fun changeSalary(userId: Int, inputSalary: String?){
        val salary = parseCount(inputSalary)
        val valid = validateSalary(salary)
        if (valid){
            viewModelScope.launch {
                val newUser = getUserUseCase(userId).copy(salary = salary)
                addUserUseCase(newUser)
                _state.value = EmployeesState.ShouldCloseModal
            }
        }
    }

    fun changePosition(userId: Int, inputPosition: String?){
        val position = parseString(inputPosition)
        val valid = validatePosition(position)
        if (valid){
            viewModelScope.launch {
                val newUser = getUserUseCase(userId).copy(position = position)
                addUserUseCase(newUser)
                _state.value = EmployeesState.ShouldCloseModal
            }
        }
    }

    fun getUserList(projectId: Int, limit: Int){
        getUserListUseCase(projectId, limit).observeForever{ userList ->
            if (userList.isEmpty()){
                _state.value = EmployeesState.ErrorEmployeesList
                return@observeForever
            }
            viewModelScope.launch {
                val totalSalary = getTotalSalaryUseCase(projectId)
                userList.map {
                    it.setPercentSalary(totalSalary)
                }
                _state.value = EmployeesState.UserList(userList)
            }
        }
    }

    fun getCountUsers(projectId: Int){
        viewModelScope.launch {
            val countUsers = getCountUsersUseCase(projectId)
            _state.value = EmployeesState.CountUsers(countUsers)
        }
    }

    fun getProjectItem(projectItemId: Int) {
        viewModelScope.launch {
            val item = getProjectUseCase(projectItemId)
            _state.value = EmployeesState.ProjectItem(item)
        }
    }

    fun addUser(
        inputFirstName: String?,
        inputLastName: String?,
        inputMiddleName: String?,
        inputAvatar: Bitmap?,
        inputPosition: String?,
        projectId: Int,
        inputSalary: String?,
        inputLogin: String?,
        inputPassword: String?,
        inputSecretWord: String?,
    ){
        val firstName = parseString(inputFirstName)
        val lastName = parseString(inputLastName)
        val middleName = parseString(inputMiddleName)
        val position = parseString(inputPosition)
        val salary = parseCount(inputSalary)
        val login = parseString(inputLogin)
        val password = parseString(inputPassword)
        val secretWord = parseString(inputSecretWord)
        val validPersonalData = validatePersonalData(firstName,lastName,middleName)
        val validPosition = validatePosition(position, salary)
        val validRegistration = validateRegistration(login, password, secretWord)
        if (validPersonalData && validPosition && validRegistration){
            viewModelScope.launch {
                val user = UserModel(
                    login = login,
                    password = password.hashCode(),
                    secretWord = secretWord,
                    firstName = firstName,
                    lastName = lastName,
                    middleName = middleName,
                    avatar = inputAvatar,
                    position = position,
                    projectId = projectId,
                    salary = salary
                )
                addUserUseCase(user)
                _state.value = EmployeesState.ShouldCloseModal
            }
        }
    }

    fun checkValidPersonalData(
        firstName: String?,
        lastName: String?,
        middleName: String?,
        avatar: Bitmap?
    ) {
        val firstName = parseString(firstName)
        val lastName = parseString(lastName)
        val middleName = parseString(middleName)
        validatePersonalData(firstName, lastName, middleName)
    }

    fun checkValidPosition(
        position: String?,
        salary: String?,
    ) {
        val position = parseString(position)
        val salary = parseCount(salary)
        validatePosition(position, salary)
    }

    private fun validateSalary(salary: Int) = if (salary <=0) {
            _state.value = EmployeesState.ErrorInputSalary
            false
        } else true

    private fun validatePosition(position: String) = if (position.isBlank()) {
        _state.value = EmployeesState.ErrorInputPosition
        false
    } else true

    private fun validateRegistration(
        login: String,
        password: String,
        secretWord: String,
    ): Boolean {
        var result = true
        if (login.length < 6) {
            _state.value = EmployeesState.ErrorInputLogin
            result = false
        }
        if (password.length < 6) {
            _state.value = EmployeesState.ErrorInputPassword
            result = false
        }
        if (secretWord.isBlank()) {
            _state.value = EmployeesState.ErrorInputSecretWord
            result = false
        }
        return result
    }

    private fun validatePersonalData(
        firstName: String,
        lastName: String,
        middleName: String,
    ): Boolean {
        var result = true
        if (firstName.isBlank()) {
            _state.value = EmployeesState.ErrorInputFirstName
            result = false
        }
        if (lastName.isBlank()) {
            _state.value = EmployeesState.ErrorInputLastName
            result = false
        }
        if (middleName.isBlank()) {
            _state.value = EmployeesState.ErrorInputMiddleName
            result = false
        }
        if (result){
            _state.value = EmployeesState.ValidationPersonalDateSuccessfully
        }
        return result
    }

    private fun validatePosition(
        position: String,
        salary: Int
    ): Boolean {
        var result = true
        if (position.isBlank()) {
            _state.value = EmployeesState.ErrorInputPosition
            result = false
        }
        if (salary <=0) {
            _state.value = EmployeesState.ErrorInputSalary
            result = false
        }
        if (result){
            _state.value = EmployeesState.ValidationPositionSuccessfully
        }
        return result
    }

    private fun parseString(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (ex: Exception) {
            0
        }
    }

}