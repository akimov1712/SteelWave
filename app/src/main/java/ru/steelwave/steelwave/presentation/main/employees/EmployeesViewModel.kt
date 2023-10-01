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
import javax.inject.Inject

class EmployeesViewModel @Inject constructor(
    private val getProjectUseCase: GetProjectUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getUserListUseCase: GetUserListUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _projectItem = MutableLiveData<ProjectModel>()
    val projectItem: LiveData<ProjectModel>
        get() = _projectItem

    private val _validationPersonalDateSuccessfully = MutableLiveData<Unit>()
    val validationPersonalDateSuccessfully: LiveData<Unit>
        get() = _validationPersonalDateSuccessfully

    // LiveDate для вывода ошибок в editText

    private val _errorInputFirstName = MutableLiveData<Boolean>()
    val errorInputFirstName: LiveData<Boolean>
        get() = _errorInputFirstName

    private val _errorInputLastName = MutableLiveData<Boolean>()
    val errorInputLastName: LiveData<Boolean>
        get() = _errorInputLastName

    private val _errorInputMiddleName = MutableLiveData<Boolean>()
    val errorInputMiddleName: LiveData<Boolean>
        get() = _errorInputMiddleName

    private val _errorInputAvatar = MutableLiveData<Unit>()
    val errorInputAvatar: LiveData<Unit>
        get() = _errorInputAvatar

    fun getProjectItem(projectItemId: Int) {
        viewModelScope.launch {
            val item = getProjectUseCase(projectItemId)
            _projectItem.value = item
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
            _errorInputFirstName.value = true
            result = false
        }
        if (lastName.isBlank()) {
            _errorInputLastName.value = true
            result = false
        }
        if (middleName.isBlank()) {
            _errorInputMiddleName.value = true
            result = false
        }
        if (avatar == null){
            _errorInputAvatar.value = Unit
            result = false
        }
        if (result){
            _validationPersonalDateSuccessfully.value = Unit
        }
    }



}