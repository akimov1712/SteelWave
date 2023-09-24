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
import java.util.Date
import javax.inject.Inject

class TrafficViewModel @Inject constructor(
    private val getProjectUseCase: GetProjectUseCase,
    private val getTransferListUseCase: GetTransferListUseCase,
    private val getVisitionUseCase: GetVisitionUseCase
) : ViewModel() {

    private val _projectItem = MutableLiveData<ProjectModel>()
    val projectItem: LiveData<ProjectModel>
        get() = _projectItem

    private val _visitionItem = MutableLiveData<VisitionModel>()
    val visitionItem: LiveData<VisitionModel>
        get() = _visitionItem

    private val _transferList = MutableLiveData<List<TransferModel>>()
    val transferList: LiveData<List<TransferModel>>
        get() = _transferList

    fun getProjectItem(projectItemId: Int) {
        viewModelScope.launch {
            val item = getProjectUseCase(projectItemId)
            _projectItem.value = item
        }
    }

    fun getVisition(projectId: Int, date: Date){
        viewModelScope.launch {
            val item = getVisitionUseCase(projectId, date)
            Loger.log("Пришел visition: ${item.value}")
            _visitionItem.value = item.value
        }
    }

    fun getTransferList(projectId: Int, startDate: Date, endDate: Date){
        viewModelScope.launch {
            val item = getTransferListUseCase(projectId, startDate, endDate)
            Loger.log("Пришел transfer: ${item.value}")
            _transferList.value = item.value
        }
    }

    fun getDate(projectId: Int, visitionDate: Date, startDateTransfer: Date, endDateTransfer: Date){
        getVisition(projectId, visitionDate)
        getTransferList(projectId, startDateTransfer, endDateTransfer)
    }

}