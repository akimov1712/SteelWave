package ru.steelwave.steelwave.presentation.main.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.domain.useCase.project.GetProjectUseCase
import ru.steelwave.steelwave.domain.useCase.user.task.GetTaskListUseCase
import ru.steelwave.steelwave.domain.useCase.user.user.GetUserUseCase
import javax.inject.Inject

class ReportViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val getTaskListUseCase: GetTaskListUseCase,
): ViewModel() {

    private val _state = MutableLiveData<ReportState>()
    val state: LiveData<ReportState>
        get() = _state

    fun getTaskList(projectId: Int, userId: Int){
        getTaskListUseCase(projectId, userId).observeForever {taskList ->
            _state.value = ReportState.TaskList(taskList)
        }
    }

    fun getUser(userId: Int){
        viewModelScope.launch {
            val user = getUserUseCase(userId)
            _state.value = ReportState.UserItem(user)
        }
    }

    fun getProject(projectId: Int){
        viewModelScope.launch {
            getProjectUseCase(projectId).also {
                _state.value = ReportState.ProjectItem(it)
            }
        }
    }

}