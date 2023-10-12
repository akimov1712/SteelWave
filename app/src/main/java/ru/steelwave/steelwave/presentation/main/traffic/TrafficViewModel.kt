package ru.steelwave.steelwave.presentation.main.traffic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.Loger
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.entity.traffic.TransferModel
import ru.steelwave.steelwave.domain.entity.traffic.VisitionModel
import ru.steelwave.steelwave.domain.useCase.project.GetProjectUseCase
import ru.steelwave.steelwave.domain.useCase.traffic.GetTransferListUseCase
import ru.steelwave.steelwave.domain.useCase.traffic.GetVisitionUseCase
import ru.steelwave.steelwave.presentation.main.finance.FinanceState
import java.util.Date
import javax.inject.Inject

class TrafficViewModel @Inject constructor(
    private val getProjectUseCase: GetProjectUseCase,
    private val getTransferListUseCase: GetTransferListUseCase,
    private val getVisitionUseCase: GetVisitionUseCase
) : ViewModel() {

    private val _state = MutableLiveData<TrafficState>()
    val state: LiveData<TrafficState>
        get() = _state

    fun getProjectItem(projectItemId: Int) {
        viewModelScope.launch {
            val item = getProjectUseCase(projectItemId)
            _state.value = TrafficState.ProjectItem(item)
        }
    }

    fun getVisition(projectId: Int, date: Date){
        viewModelScope.launch {
            val item = getVisitionUseCase(projectId, date)
            item.value?.let {
                _state.value = TrafficState.VisitionItem(it)
            }
        }
    }

    fun getTransferList(projectId: Int, startDate: Date, endDate: Date){
        viewModelScope.launch {
            val item = getTransferListUseCase(projectId, startDate, endDate)
            item.value?.let{
                _state.value = TrafficState.TransferList(it)
            }
        }
    }

    fun getDate(projectId: Int, visitionDate: Date, startDateTransfer: Date, endDateTransfer: Date){
        getVisition(projectId, visitionDate)
        getTransferList(projectId, startDateTransfer, endDateTransfer)
    }

}