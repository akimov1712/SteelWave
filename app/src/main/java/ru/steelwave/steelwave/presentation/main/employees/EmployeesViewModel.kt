package ru.steelwave.steelwave.presentation.main.employees

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.useCase.project.GetProjectUseCase
import ru.steelwave.steelwave.domain.useCase.user.AddUserUseCase
import ru.steelwave.steelwave.domain.useCase.user.DeleteUserUseCase
import ru.steelwave.steelwave.domain.useCase.user.GetUserListUseCase
import ru.steelwave.steelwave.domain.useCase.user.GetUserUseCase
import ru.steelwave.steelwave.presentation.main.finance.FinanceState
import javax.inject.Inject

class EmployeesViewModel @Inject constructor(
    private val getProjectUseCase: GetProjectUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getUserListUseCase: GetUserListUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _state = MutableLiveData<EmployeesState>()
    val state: LiveData<EmployeesState>
        get() = _state

    fun getProjectItem(projectItemId: Int) {
        viewModelScope.launch {
            val item = getProjectUseCase(projectItemId)
            _state.value = EmployeesState.ProjectItem(item)
        }
    }

    private fun validatePersonalData(
        firstName: String,
        lastName: String,
        middleName: String,
        avatar: Bitmap?
    ) {
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
    }



}