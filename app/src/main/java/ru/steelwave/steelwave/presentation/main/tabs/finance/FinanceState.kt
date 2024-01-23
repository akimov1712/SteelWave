package ru.steelwave.steelwave.presentation.main.finance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.steelwave.steelwave.domain.entity.finance.TargetModel
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import ru.steelwave.steelwave.domain.entity.finance.YearIncomeModel
import ru.steelwave.steelwave.domain.entity.project.ProjectModel

sealed class FinanceState{

    object ShouldCloseDeleteTargetModal: FinanceState()
    object ShouldCloseRefillTargetModal: FinanceState()
    object ShouldCloseAddTargetModal: FinanceState()
    object ShouldCloseAddLossModal: FinanceState()

    object ErrorInputNameAddLoss: FinanceState()
    object ErrorInputCount: FinanceState()
    object ErrorInputNameAddTarget: FinanceState()
    object ErrorInputStartPrice: FinanceState()
    object ErrorInputEndPrice: FinanceState()
    object ErrorInputRefill: FinanceState()

    object YearIncomeItemError: FinanceState()
    object LossItemError: FinanceState()
    object IncomeItemError: FinanceState()
    object TargetListError: FinanceState()

    data class ProjectItem(
        val projectItem: ProjectModel
    ): FinanceState()

    data class IncomeList(val incomeList: List<TransactionModel> = emptyList() ): FinanceState()
    data class LossList(val lossList: List<TransactionModel> = emptyList()): FinanceState()
    data class TargetList(val targetList: List<TargetModel> = emptyList()): FinanceState()
    data class TargetItem(val targetItem: TargetModel): FinanceState()
    data class YearIncomeItem(val yearIncomeItem: YearIncomeModel): FinanceState()

}
