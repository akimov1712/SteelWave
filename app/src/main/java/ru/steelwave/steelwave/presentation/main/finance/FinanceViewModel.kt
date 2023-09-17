package ru.steelwave.steelwave.presentation.main.finance

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.Loger
import ru.steelwave.steelwave.domain.entity.finance.IncomeModel
import ru.steelwave.steelwave.domain.entity.finance.LossModel
import ru.steelwave.steelwave.domain.entity.finance.TargetModel
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import ru.steelwave.steelwave.domain.entity.finance.YearIncomeModel
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.useCase.finance.income.GetIncomeUseCase
import ru.steelwave.steelwave.domain.useCase.finance.incomeYear.GetYearIncomeUseCase
import ru.steelwave.steelwave.domain.useCase.finance.loss.AddLossUseCase
import ru.steelwave.steelwave.domain.useCase.finance.loss.GetLossUseCase
import ru.steelwave.steelwave.domain.useCase.finance.target.AddTargetUseCase
import ru.steelwave.steelwave.domain.useCase.finance.target.GetAllTargetUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetAllProjectUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetProjectUseCase
import java.sql.Date
import javax.inject.Inject

class FinanceViewModel @Inject constructor(
    private val getAllProjectUseCase: GetAllProjectUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val getIncomeUseCase: GetIncomeUseCase,
    private val getYearIncomeUseCase: GetYearIncomeUseCase,
    private val addLossUseCase: AddLossUseCase,
    private val getLossUseCase: GetLossUseCase,
    private val addTargetUseCase: AddTargetUseCase,
    private val getAllTargetUseCase: GetAllTargetUseCase,
    ) : ViewModel() {

    private val _projectItem = MutableLiveData<ProjectModel>()
    val projectItem: LiveData<ProjectModel>
        get() = _projectItem

    private val _incomeItem = MutableLiveData<IncomeModel>()
    val incomeItem: LiveData<IncomeModel>
        get() = _incomeItem

    private val _incomeItemError = MutableLiveData<Unit>()
    val incomeItemError: LiveData<Unit>
        get() = _incomeItemError

    private val _yearIncomeItem = MutableLiveData<YearIncomeModel>()
    val yearIncomeItem: LiveData<YearIncomeModel>
        get() = _yearIncomeItem

    private val _yearIncomeItemError = MutableLiveData<Unit>()
    val yearIncomeItemError: LiveData<Unit>
        get() = _yearIncomeItemError

    private val _lossItem = MutableLiveData<LossModel>()
    val lossItem: LiveData<LossModel>
        get() = _lossItem

    private val _lossItemError = MutableLiveData<Unit>()
    val lossItemError: LiveData<Unit>
        get() = _lossItemError

    private val _targetList = MutableLiveData<List<TargetModel>>()
    val targetList: LiveData<List<TargetModel>>
        get() = _targetList

    private val _targetListError = MutableLiveData<List<Unit>>()
    val targetListError: LiveData<List<Unit>>
        get() = _targetListError

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shouldRefreshData = MutableLiveData<Unit>()
    val shouldRefreshData: LiveData<Unit>
        get() = _shouldRefreshData

    val projectList = getAllProjectUseCase()


    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (ex: Exception) {
            0
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    fun addLoss(projectId: Int, inputDate: Long, inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid){
            viewModelScope.launch {
                val transaction = TransactionModel(name,count)
                val date = Date(inputDate)
                val lossItem = getLossUseCase(projectId, date)
                if (lossItem == null){
                    val loss = LossModel(projectId = projectId, date = inputDate)
                    loss.addTransaction(transaction)
                    addLossUseCase(loss)
                } else {
                    lossItem?.let {
                        val loss = lossItem.copy()
                        loss.addTransaction(transaction)
                        addLossUseCase(loss)
                    }
                }
            }
        }
    }

    fun getProjectItem(projectItemId: Int) {
        viewModelScope.launch {
            val item = getProjectUseCase(projectItemId)
            _projectItem.value = item
        }
    }

    fun getIncomeItem(projectId: Int, date: Date) {
        viewModelScope.launch {
            val item = getIncomeUseCase(projectId, date)
            if(item == null){
                _incomeItemError.value = Unit
            }else{
                _incomeItem.value = item!!
            }
        }
    }

    fun getLossItem(projectId: Int, date: Date) {
        viewModelScope.launch {
            val item = getLossUseCase(projectId, date)
            if(item == null){
                _lossItemError.value = Unit
            }else{
                _lossItem.value = item!!
            }
        }
    }

    fun getTargetList(projectId: Int) {
        viewModelScope.launch {
            val item = getAllTargetUseCase(projectId)
            _targetList.value = item.value
        }
    }

    fun getYearIncomeItem(projectId: Int, inputYear: Date) {
        viewModelScope.launch {
            val year = inputYear.year + 1900
            val item = getYearIncomeUseCase(projectId, year)
            if(item == null){
                _yearIncomeItemError.value = Unit
            }else{
                _yearIncomeItem.value = item!!
            }
        }
    }

    fun getData(projectId: Int, inputDate:Date, inputYear: Date){
        getIncomeItem(projectId, inputDate)
        getLossItem(projectId, inputDate)
        getTargetList(projectId)
        getYearIncomeItem(projectId, inputYear)
    }

    fun notifyDataRefreshNeeded() {
        _shouldRefreshData.value = Unit
    }

}