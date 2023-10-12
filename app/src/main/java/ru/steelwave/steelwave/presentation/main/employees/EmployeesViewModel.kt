package ru.steelwave.steelwave.presentation.main.employees

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.domain.entity.user.UserModel
import ru.steelwave.steelwave.domain.useCase.project.GetAllProjectUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetProjectUseCase
import ru.steelwave.steelwave.domain.useCase.user.AddUserUseCase
import ru.steelwave.steelwave.domain.useCase.user.DeleteUserUseCase
import ru.steelwave.steelwave.domain.useCase.user.GetUserListUseCase
import ru.steelwave.steelwave.domain.useCase.user.GetUserUseCase
import javax.inject.Inject

class EmployeesViewModel @Inject constructor(
    private val getProjectUseCase: GetProjectUseCase,
    private val getProjectListUseCase: GetAllProjectUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getUserListUseCase: GetUserListUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _state = MutableLiveData<EmployeesState>()
    val state: LiveData<EmployeesState>
        get() = _state

    private val _projectList = MutableLiveData<List<String>>()
    val projectList: LiveData<List<String>>
        get() = _projectList

    private fun getProjectList(){
        getProjectListUseCase().observeForever{
            val listProject = mutableListOf<String>()
            it.map { project ->
                listProject.add(project.name)
            }
            _projectList.value = listProject
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
        inputProject: String?,
        inputSalary: String?,
        inputLogin: String?,
        inputPassword: String?,
        inputSecretWord: String?,
    ){
        val firstName = parseString(inputFirstName)
        val lastName = parseString(inputLastName)
        val middleName = parseString(inputMiddleName)
        val position = parseString(inputPosition)
        val project = parseString(inputProject)
        val salary = parseCount(inputSalary)
        val login = parseString(inputLogin)
        val password = parseString(inputPassword)
        val secretWord = parseString(inputSecretWord)
        val validPersonalData = validatePersonalData(firstName,lastName,middleName,inputAvatar)
        val validPosition = validatePosition(position, project, salary)
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
                    project = project,
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
        validatePersonalData(firstName, lastName, middleName, avatar)
    }

    fun checkValidRegistration(
        login: String?,
        password: String?,
        secretWord: String?,
    ) {
        val login = parseString(login)
        val password = parseString(password)
        val secretWord = parseString(secretWord)
        validateRegistration(login, password, secretWord)
    }

    fun checkValidPosition(
        position: String?,
        project: String?,
        salary: String?,
    ) {
        val position = parseString(position)
        val project = parseString(project)
        val salary = parseCount(salary)
        validatePosition(position, project, salary)
    }

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
        avatar: Bitmap?
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
        if (avatar == null){
            _state.value = EmployeesState.ErrorInputAvatar
            result = false
        }
        if (result){
            _state.value = EmployeesState.ValidationPersonalDateSuccessfully
        }
        return result
    }

    private fun validatePosition(
        position: String,
        project: String,
        salary: Int
    ): Boolean {
        var result = true
        if (position.isBlank()) {
            _state.value = EmployeesState.ErrorInputPosition
            result = false
        }
        if (project.isBlank()) {
            _state.value = EmployeesState.ErrorInputProject
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

    init {
        getProjectList()
    }

}