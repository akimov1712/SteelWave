package ru.steelwave.steelwave.presentation.main.finance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.useCase.finance.income.GetIncomeUseCase
import ru.steelwave.steelwave.domain.useCase.finance.incomeYear.GetYearIncomeUseCase
import ru.steelwave.steelwave.domain.useCase.finance.loss.AddLossUseCase
import ru.steelwave.steelwave.domain.useCase.finance.loss.GetLossUseCase
import ru.steelwave.steelwave.domain.useCase.finance.target.AddTargetUseCase
import ru.steelwave.steelwave.domain.useCase.finance.target.GetAllTargetUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetAllProjectUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetProjectUseCase
import javax.inject.Inject

class FinanceViewModel @Inject constructor(
    private val getAllProjectUseCase: GetAllProjectUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val getIncomeUseCase: GetIncomeUseCase,
    private val getYearIncomeUseCase: GetYearIncomeUseCase,
    private val addLossUseCase: AddLossUseCase,
    private val getLossUseCase: GetLossUseCase,
    private val addTargetUseCase: AddTargetUseCase,
    private val getAllTargetUseCase: GetAllTargetUseCase
    ) : ViewModel() {

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

}