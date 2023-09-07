package ru.steelwave.steelwave.presentation.main.project

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.useCase.project.AddProjectUseCase
import ru.steelwave.steelwave.domain.useCase.project.DeleteProjectUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetAllProjectUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetProjectUseCase
import javax.inject.Inject

class ProjectViewModel @Inject constructor(
    private val getAllProjectUseCase: GetAllProjectUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val addProjectUseCase: AddProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase
) : ViewModel() {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorImage = MutableLiveData<Unit>()
    val errorImage: LiveData<Unit>
        get() = _errorImage

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private val _projectItem = MutableLiveData<ProjectModel>()
    val projectItem: LiveData<ProjectModel>
        get() = _projectItem

    val projectList = getAllProjectUseCase()

    fun getProjectItem(projectItemId: Int) {
        viewModelScope.launch {
            val item = getProjectUseCase(projectItemId)
            _projectItem.value = item
        }
    }

    fun deleteProjectItem(){
        viewModelScope.launch {
            _projectItem.value?.let { deleteProjectUseCase(it) }
        }
    }

    fun addProject(inputName: String?, inputImage: Bitmap?, inputCreatedDate: Long?) {
        val name = parseName(inputName)
        val createdDate = parseCreatedDate(inputCreatedDate)
        val fieldsValid = validateInput(name, inputImage, createdDate)
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
        val fieldsValid = validateInput(name, inputImage, createdDate)
        if (fieldsValid){
            _projectItem.value?.let {
                viewModelScope.launch {
                    val projectItem = it.copy(name = name, previewImage = inputImage, dateRelease = createdDate)
                    addProjectUseCase(projectItem)
                    finishWork()
                }
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCreatedDate(createdDate: Long?): Long {
        return createdDate ?: System.currentTimeMillis()
    }

    private fun validateInput(name: String, image: Bitmap?, createdDate: Long): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (image == null){
            _errorImage.value = Unit
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

}