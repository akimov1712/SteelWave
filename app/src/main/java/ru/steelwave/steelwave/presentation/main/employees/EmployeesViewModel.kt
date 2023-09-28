package ru.steelwave.steelwave.presentation.main.employees

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

    fun getProjectItem(projectItemId: Int) {
        viewModelScope.launch {
            val item = getProjectUseCase(projectItemId)
            _projectItem.value = item
        }
    }

}