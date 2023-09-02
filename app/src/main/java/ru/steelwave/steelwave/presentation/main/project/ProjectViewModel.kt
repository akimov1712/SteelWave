package ru.steelwave.steelwave.presentation.main.project

import androidx.lifecycle.ViewModel
import ru.steelwave.steelwave.domain.useCase.project.AddProjectUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetAllProjectUseCase
import javax.inject.Inject

class ProjectViewModel @Inject constructor(
    private val getAllProjectUseCase: GetAllProjectUseCase,
    private val addProjectUseCase: AddProjectUseCase,
) : ViewModel() {


}