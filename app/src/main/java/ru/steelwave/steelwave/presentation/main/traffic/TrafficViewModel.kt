package ru.steelwave.steelwave.presentation.main.traffic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.domain.useCase.project.GetProjectUseCase
import ru.steelwave.steelwave.domain.useCase.traffic.GetTransferListUseCase
import ru.steelwave.steelwave.domain.useCase.traffic.GetVisitionUseCase
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TrafficViewModel @Inject constructor(
    private val getProjectUseCase: GetProjectUseCase,
    private val getTransferListUseCase: GetTransferListUseCase,
    private val getVisitionUseCase: GetVisitionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<TrafficState>(TrafficState.Loading)
    val state = _state.asStateFlow()

    fun getProjectItem(projectItemId: Int) {
        viewModelScope.launch {
            val item = getProjectUseCase(projectItemId)
            _state.value = TrafficState.ProjectItem(item)
        }
    }

    fun getVisition(projectId: Int, date: Date) {
        viewModelScope.launch {
            getVisitionUseCase(projectId, date).collect {
                _state.value = TrafficState.VisitionItem(it)
            }
        }
    }

    fun getTransferList(projectId: Int, startDate: Date, endDate: Date) {
        viewModelScope.launch {
            getTransferListUseCase(projectId, startDate, endDate).collect {
                _state.value = TrafficState.TransferList(it)
            }
        }
    }

    fun getDate(
        projectId: Int,
        visitionDate: Date,
        startDateTransfer: Date,
        endDateTransfer: Date
    ) {
        getVisition(projectId, visitionDate)
        getTransferList(projectId, startDateTransfer, endDateTransfer)
    }

}