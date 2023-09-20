package ru.steelwave.steelwave.presentation.main.finance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.Loger
import ru.steelwave.steelwave.domain.entity.finance.TargetModel
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import ru.steelwave.steelwave.domain.entity.finance.YearIncomeModel
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.useCase.finance.incomeYear.GetYearIncomeUseCase
import ru.steelwave.steelwave.domain.useCase.finance.target.AddTargetUseCase
import ru.steelwave.steelwave.domain.useCase.finance.target.DeleteTargetUseCase
import ru.steelwave.steelwave.domain.useCase.finance.target.GetAllTargetUseCase
import ru.steelwave.steelwave.domain.useCase.finance.target.GetTargetItemUseCase
import ru.steelwave.steelwave.domain.useCase.finance.transaction.AddTransactionUseCase
import ru.steelwave.steelwave.domain.useCase.finance.transaction.GetTransactionListUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetAllProjectUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetProjectUseCase
import java.sql.Date
import javax.inject.Inject

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

    // LiveDate для вывода контента

    val projectList = getAllProjectUseCase()

    private val _projectItem = MutableLiveData<ProjectModel>()
    val projectItem: LiveData<ProjectModel>
        get() = _projectItem

    private val _incomeList = MutableLiveData<List<TransactionModel>>()
    val incomeList: LiveData<List<TransactionModel>>
        get() = _incomeList

    private val _lossList = MutableLiveData<List<TransactionModel>>()
    val lossList: LiveData<List<TransactionModel>>
        get() = _lossList

    private val _targetList = MutableLiveData<List<TargetModel>>()
    val targetList: LiveData<List<TargetModel>>
        get() = _targetList

    private val _targetItem = MutableLiveData<TargetModel>()
    val targetItem: LiveData<TargetModel>
        get() = _targetItem

    private val _yearIncomeItem = MutableLiveData<YearIncomeModel>()
    val yearIncomeItem: LiveData<YearIncomeModel>
        get() = _yearIncomeItem

    // LiveDate для вывода ошибок при получении данных

    private val _yearIncomeItemError = MutableLiveData<Unit>()
    val yearIncomeItemError: LiveData<Unit>
        get() = _yearIncomeItemError

    private val _lossItemError = MutableLiveData<Unit>()
    val lossItemError: LiveData<Unit>
        get() = _lossItemError

    private val _incomeItemError = MutableLiveData<Unit>()
    val incomeItemError: LiveData<Unit>
        get() = _incomeItemError

    private val _targetListError = MutableLiveData<Unit>()
    val targetListError: LiveData<Unit>
        get() = _targetListError

    // LiveDate для вывода ошибок в editText

    private val _errorInputNameAddLoss = MutableLiveData<Boolean>()
    val errorInputNameAddLoss: LiveData<Boolean>
        get() = _errorInputNameAddLoss

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _errorInputNameAddTarget = MutableLiveData<Boolean>()
    val errorInputNameAddTarget: LiveData<Boolean>
        get() = _errorInputNameAddTarget

    private val _errorInputStartPrice = MutableLiveData<Boolean>()
    val errorInputStartPrice: LiveData<Boolean>
        get() = _errorInputStartPrice

    private val _errorInputEndPrice = MutableLiveData<Boolean>()
    val errorInputEndPrice: LiveData<Boolean>
        get() = _errorInputEndPrice

    private val _errorInputRefill = MutableLiveData<Boolean>()
    val errorInputRefill: LiveData<Boolean>
        get() = _errorInputRefill

    // LiveDate для закрытия экрана

    private val _shouldCloseAddLossModal = MutableLiveData<Unit>()
    val shouldCloseAddLossModal: LiveData<Unit>
        get() = _shouldCloseAddLossModal

    private val _shouldCloseAddTargetModal = MutableLiveData<Unit>()
    val shouldCloseAddTargetModal: LiveData<Unit>
        get() = _shouldCloseAddTargetModal

    private val _shouldCloseRefillTargetModal = MutableLiveData<Unit>()
    val shouldCloseRefillTargetModal: LiveData<Unit>
        get() = _shouldCloseRefillTargetModal

    private val _shouldCloseDeleteTargetModal = MutableLiveData<Unit>()
    val shouldCloseDeleteTargetModal: LiveData<Unit>
        get() = _shouldCloseDeleteTargetModal


    private fun validateInputAddLoss(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputNameAddLoss.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    private fun validateInputAddTarget(name: String, collectedPrice: Int,totalPrice: Int,): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputNameAddTarget.value = true
            result = false
        }
        if (totalPrice <= 0) {
            _errorInputEndPrice.value = true
            result = false
        }
        if (totalPrice <= 0 || collectedPrice > totalPrice) {
            _errorInputStartPrice.value = true
            result = false
        }
        return result
    }

    private fun validateInputRefillTarget(count: Int): Boolean {
        var result = true
        if (count <= 0) {
            _errorInputRefill.value = true
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
            getTransactionListUseCase(projectId, date).observeForever { resultList ->
                val incomeList = resultList.filter {
                    it.isIncome
                }
                val lossList = resultList.filter {
                    !it.isIncome
                }
                if (incomeList.isNotEmpty()) {
                    _incomeList.value = incomeList!!
                } else {
                    _incomeItemError.value = Unit
                }
                if (lossList.isNotEmpty()) {
                    _lossList.value = lossList!!
                } else {
                    _lossItemError.value = Unit
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
                _shouldCloseAddLossModal.value = Unit
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
                _shouldCloseAddTargetModal.value = Unit
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
                _shouldCloseAddTargetModal.value = Unit
            }
        }
    }

    fun getProjectItem(projectItemId: Int) {
        viewModelScope.launch {
            val item = getProjectUseCase(projectItemId)
            _projectItem.value = item
        }
    }

    fun getTargetList(projectId: Int) {
        viewModelScope.launch {
            getAllTargetUseCase(projectId).observeForever { resultList ->
                if (resultList.isNotEmpty()) {
                    _targetList.value = resultList!!
                } else {
                    _targetListError.value = Unit
                }
            }
        }
    }

    fun getTargetItem(targetId: Int) {
        viewModelScope.launch {
            val item = getTargetItemUseCase(targetId)
            _targetItem.value = item
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
                _shouldCloseRefillTargetModal.value = Unit
            }
        }
    }

    fun deleteTarget(targetId: Int) {
        viewModelScope.launch {
            deleteTargetUseCase(targetId)
            _shouldCloseDeleteTargetModal.value = Unit
        }
    }

    fun getYearIncomeItem(projectId: Int, inputYear: Date) {
        viewModelScope.launch {
            val year = inputYear.year + 1900
            val item = getYearIncomeUseCase(projectId, year)
            if (item == null) {
                _yearIncomeItemError.value = Unit
            } else {
                _yearIncomeItem.value = item!!
            }
        }
    }

    fun getData(projectId: Int, inputDate: Date, inputYear: Date) {
        getTransactionList(projectId, inputDate)
        getTargetList(projectId)
        getYearIncomeItem(projectId, inputYear)
    }

}