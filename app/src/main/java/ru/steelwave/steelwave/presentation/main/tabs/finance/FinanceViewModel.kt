package ru.steelwave.steelwave.presentation.main.finance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.domain.entity.finance.TargetModel
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import ru.steelwave.steelwave.domain.entity.finance.YearIncomeModel
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.useCase.finance.GetYearIncomeUseCase
import ru.steelwave.steelwave.domain.useCase.finance.AddTargetUseCase
import ru.steelwave.steelwave.domain.useCase.finance.DeleteTargetUseCase
import ru.steelwave.steelwave.domain.useCase.finance.GetAllTargetUseCase
import ru.steelwave.steelwave.domain.useCase.finance.GetTargetItemUseCase
import ru.steelwave.steelwave.domain.useCase.finance.AddTransactionUseCase
import ru.steelwave.steelwave.domain.useCase.finance.GetTransactionListUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetAllProjectUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetProjectUseCase
import java.sql.Date
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val getAllProjectUseCase: GetAllProjectUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val getYearIncomeUseCase: GetYearIncomeUseCase,
    private val addTargetUseCase: AddTargetUseCase,
    private val getAllTargetUseCase: GetAllTargetUseCase,
    private val getTargetItemUseCase: GetTargetItemUseCase,
    private val deleteTargetUseCase: DeleteTargetUseCase,
    private val getTransactionListUseCase: GetTransactionListUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
) : ViewModel() {

    val projectList = getAllProjectUseCase()

    private val _state = MutableLiveData<FinanceState>()
    val state: LiveData<FinanceState>
        get() = _state

    private fun validateInputAddLoss(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _state.value = FinanceState.ErrorInputNameAddLoss
            result = false
        }
        if (count <= 0) {
            _state.value = FinanceState.ErrorInputCount
            result = false
        }
        return result
    }

    private fun validateInputAddTarget(name: String, collectedPrice: Int,totalPrice: Int,): Boolean {
        var result = true
        if (name.isBlank()) {
            _state.value = FinanceState.ErrorInputNameAddTarget
            result = false
        }
        if (totalPrice <= 0) {
            _state.value = FinanceState.ErrorInputEndPrice
            result = false
        }
        if (totalPrice <= 0 || collectedPrice > totalPrice) {
            _state.value = FinanceState.ErrorInputStartPrice
            result = false
        }
        return result
    }

    private fun validateInputRefillTarget(count: Int): Boolean {
        var result = true
        if (count <= 0) {
            _state.value = FinanceState.ErrorInputRefill
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

    fun getTransactionList(projectId: Int, date: Date) {
        viewModelScope.launch {
            getTransactionListUseCase(projectId, date).collect { resultList ->
                val incomeList = resultList.filter {
                    it.isIncome
                }
                val lossList = resultList.filter {
                    !it.isIncome
                }
                if (incomeList.isNotEmpty()) {
                    _state.value = FinanceState.IncomeList(incomeList)
                } else {
                    _state.value = FinanceState.IncomeItemError
                }
                if (lossList.isNotEmpty()) {
                    _state.value = FinanceState.LossList(lossList)
                } else {
                    _state.value = FinanceState.LossItemError
                }
            }
        }
    }

    fun addTransaction(
        projectId: Int,
        inputDate: Long,
        isIncome: Boolean,
        inputName: String?,
        inputCount: String?
    ) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInputAddLoss(name, count)
        if (fieldsValid) {
            viewModelScope.launch {
                val date = Date(inputDate)
                val transaction = TransactionModel(
                    projectId = projectId,
                    date = date,
                    isIncome = isIncome,
                    name = name,
                    count = count
                )
                addTransactionUseCase(transaction)
                _state.value = FinanceState.ShouldCloseAddLossModal
            }
        }
    }

    fun addTarget(projectId: Int, inputName: String?, inputCollectedPrice: String?, inputTotalPrice: String?){
        viewModelScope.launch {
            val name = parseName(inputName)
            val collectedPrice = parseCount(inputCollectedPrice)
            val totalPrice = parseCount(inputTotalPrice)
            val isFinished = collectedPrice == totalPrice
            val fieldsValid = validateInputAddTarget(name, collectedPrice, totalPrice)
            if (fieldsValid){
                val target = TargetModel(
                    projectId = projectId,
                    name = name,
                    collectedPrice = collectedPrice,
                    totalPrice = totalPrice,
                    isFinished = isFinished
                )
                addTargetUseCase(target)
                _state.value = FinanceState.ShouldCloseAddTargetModal
            }
        }
    }

    fun updateTarget(targetId: Int, inputName: String?, inputCollectedPrice: String?, inputTotalPrice: String?){
        viewModelScope.launch {
            val name = parseName(inputName)
            val collectedPrice = parseCount(inputCollectedPrice)
            val totalPrice = parseCount(inputTotalPrice)
            val isFinished = collectedPrice == totalPrice
            val fieldsValid = validateInputAddTarget(name, collectedPrice, totalPrice)
            if (fieldsValid){
                val oldTarget = getTargetItemUseCase(targetId)
                val newTarget = oldTarget.copy(
                    name = name,
                    collectedPrice = collectedPrice,
                    totalPrice = totalPrice,
                    isFinished = isFinished
                )
                addTargetUseCase(newTarget)
                _state.value = FinanceState.ShouldCloseAddTargetModal
            }
        }
    }

    fun getProjectItem(projectItemId: Int) {
        viewModelScope.launch {
            val item = getProjectUseCase(projectItemId)
            _state.value = FinanceState.ProjectItem(item)
        }
    }

    fun getTargetList(projectId: Int) {
        viewModelScope.launch {
            getAllTargetUseCase(projectId).collect { resultList ->
                if (resultList.isNotEmpty()) {
                    _state.value = FinanceState.TargetList(resultList)
                } else {
                    _state.value = FinanceState.TargetListError
                }
            }
        }
    }

    fun getTargetItem(targetId: Int) {
        viewModelScope.launch {
            val item = getTargetItemUseCase(targetId)
            _state.value = FinanceState.TargetItem(item)
        }
    }

    fun refillTarget(targetId: Int, inputCount: String?) {
        viewModelScope.launch {
            val oldItem = getTargetItemUseCase(targetId)
            val count = parseCount(inputCount)
            val fieldsValid = validateInputRefillTarget(count)
            if (fieldsValid){
                val refillPrice = oldItem.collectedPrice + count
                val newItem = oldItem.copy(collectedPrice = refillPrice)
                if (refillPrice > newItem.totalPrice) newItem.isFinished = true
                addTargetUseCase(newItem)
                _state.value = FinanceState.ShouldCloseRefillTargetModal
            }
        }
    }

    fun deleteTarget(targetId: Int) {
        viewModelScope.launch {
            deleteTargetUseCase(targetId)
            _state.value = FinanceState.ShouldCloseDeleteTargetModal
        }
    }

    fun getYearIncomeItem(projectId: Int, inputYear: Date) {
        viewModelScope.launch {
            val year = inputYear.year + 1900
            val item = getYearIncomeUseCase(projectId, year)
            if (item == null) {
                _state.value = FinanceState.YearIncomeItemError
            } else {
                _state.value = FinanceState.YearIncomeItem(item)
            }
        }
    }

    fun getData(projectId: Int, inputDate: Date, inputYear: Date) {
        getTransactionList(projectId, inputDate)
        getTargetList(projectId)
        getYearIncomeItem(projectId, inputYear)
    }

}