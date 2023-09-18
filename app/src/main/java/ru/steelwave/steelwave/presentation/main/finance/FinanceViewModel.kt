package ru.steelwave.steelwave.presentation.main.finance

import android.util.Log
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
import ru.steelwave.steelwave.domain.useCase.finance.target.GetAllTargetUseCase
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
    private val getTransactionListUseCase: GetTransactionListUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
) : ViewModel() {

    private val _projectItem = MutableLiveData<ProjectModel>()
    val projectItem: LiveData<ProjectModel>
        get() = _projectItem

//    private val _transactionList = MutableLiveData<List<TransactionModel>>()
//    val transactionList: LiveData<List<TransactionModel>>
//        get() = _transactionList

    private val _incomeList = MutableLiveData<List<TransactionModel>>()
    val incomeList: LiveData<List<TransactionModel>>
        get() = _incomeList

    private val _lossList = MutableLiveData<List<TransactionModel>>()
    val lossList: LiveData<List<TransactionModel>>
        get() = _lossList

    private val _incomeItemError = MutableLiveData<Unit>()
    val incomeItemError: LiveData<Unit>
        get() = _incomeItemError

    private val _yearIncomeItem = MutableLiveData<YearIncomeModel>()
    val yearIncomeItem: LiveData<YearIncomeModel>
        get() = _yearIncomeItem

    private val _yearIncomeItemError = MutableLiveData<Unit>()
    val yearIncomeItemError: LiveData<Unit>
        get() = _yearIncomeItemError

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

    val projectList = getAllProjectUseCase()
    val transactionList = getTransactionListUseCase()


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

    fun getTransactionList() {
        val resultList = getTransactionListUseCase()
//        _transactionList.value = resultList.value
//            Loger.log(resultList.toString() + " со значениями projectId: $projectId, date: ${date.time}")
//            val incomeList = resultList?.filter {
//                it.isIncome
//            }
//            val lossList = resultList?.filter {
//                !it.isIncome
//            }
//            if (incomeList != null) {
//                _incomeList.value = incomeList!!
//            } else {
//                _incomeItemError.value = Unit
//            }
//            if (lossList != null) {
//                _lossList.value = lossList!!
//            } else {
//                _lossItemError.value = Unit
//            }
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
        val fieldsValid = validateInput(name, count)
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
            val item = getAllTargetUseCase(projectId)
            _targetList.value = item.value
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
//        getTransactionList(projectId, inputDate)
        getTargetList(projectId)
        getYearIncomeItem(projectId, inputYear)
    }

}