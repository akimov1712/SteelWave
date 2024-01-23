package ru.steelwave.steelwave.presentation.main.project

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.useCase.project.AddProjectUseCase
import ru.steelwave.steelwave.domain.useCase.project.DeleteProjectUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetAllProjectUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetProjectUseCase
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val getAllProjectUseCase: GetAllProjectUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val addProjectUseCase: AddProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ProjectState>(ProjectState.Loading)
    val state = _state.asStateFlow()

    fun getProjectItem(projectItemId: Int) = viewModelScope.launch {
        val item = getProjectUseCase(projectItemId)
        _state.value = ProjectState.ProjectItem(item)
    }

    private fun getProjectList() = viewModelScope.launch{
        getAllProjectUseCase().collect{
            _state.value = ProjectState.ProjectList(it)
        }
    }

    fun deleteProjectItem(projectId: Int){
        viewModelScope.launch {
            deleteProjectUseCase(projectId)
            finishWork()
        }
    }

    fun addProject(inputName: String?, inputImage: Bitmap?, inputCreatedDate: Long?) {
        val name = parseName(inputName)
        val createdDate = parseCreatedDate(inputCreatedDate)
        val fieldsValid = validateInput(name, inputImage)
        if (fieldsValid){
            viewModelScope.launch {
                val projectItem = ProjectModel(name = name, previewImage = inputImage, dateRelease = createdDate)
                addProjectUseCase(projectItem)
                finishWork()
            }
        }
    }

    fun editProject(inputName: String?, inputImage: Bitmap?, inputCreatedDate: Long?) {
        val name = parseName(inputName)
        val createdDate = parseCreatedDate(inputCreatedDate)
        val fieldsValid = validateInput(name, inputImage)
        if (fieldsValid) {
            viewModelScope.launch {
                val project = _state.value as ProjectState.ProjectItem
                val projectItem = project.item.copy(
                    name = name,
                    previewImage = inputImage,
                    dateRelease = createdDate
                )
                addProjectUseCase(projectItem)
                finishWork()
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCreatedDate(createdDate: Long?): Long {
        return createdDate ?: System.currentTimeMillis()
    }

    private fun validateInput(name: String, image: Bitmap?): Boolean {
        var result = true
        if (name.isBlank()) {
            _state.value = ProjectState.ErrorInputName
            result = false
        }
        if (image == null){
            _state.value = ProjectState.ErrorImage
            result = false
        }
        return result
    }

    private fun finishWork() {
        _state.value = ProjectState.ShouldCloseScreen
    }

    init {
        getProjectList()
    }

}