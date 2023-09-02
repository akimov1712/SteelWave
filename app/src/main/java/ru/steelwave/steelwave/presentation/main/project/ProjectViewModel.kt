package ru.steelwave.steelwave.presentation.main.project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import ru.steelwave.steelwave.data.repository.ProjectRepositoryImpl
import ru.steelwave.steelwave.domain.useCase.project.AddProjectUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetAllProjectUseCase

class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProjectRepositoryImpl(application)

    private val getAllProjectUseCase = GetAllProjectUseCase(repository)
    private val addProjectUseCase = AddProjectUseCase(repository)



}