package ru.steelwave.steelwave.presentation.main.finance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearSnapHelper
import com.kal.rackmonthpicker.RackMonthPicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.Const
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.FragmentFinanceBinding
import ru.steelwave.steelwave.domain.entity.finance.TargetModel
import ru.steelwave.steelwave.presentation.base.CustomToast
import ru.steelwave.steelwave.presentation.main.finance.adapters.incomeAdapter.IncomeAdapter
import ru.steelwave.steelwave.presentation.main.finance.adapters.lossAdapter.LossAdapter
import ru.steelwave.steelwave.presentation.main.finance.adapters.targetAdapter.TargetAdapter
import ru.steelwave.steelwave.presentation.main.finance.adapters.yearIncomeAdapter.YearIncomeAdapter
import ru.steelwave.steelwave.utils.formatPrice
import java.sql.Date
import java.util.Calendar

@AndroidEntryPoint
class FinanceFragment : Fragment() {

    private val args by navArgs<FinanceFragmentArgs>()
    private var projectId = Const.UNDEFINED_ID


    private var _binding: FragmentFinanceBinding? = null
    private val binding: FragmentFinanceBinding
        get() = _binding ?: throw RuntimeException("FragmentFinanceBinding == null")

    private val viewModel by viewModels<FinanceViewModel>()


    private val incomeAdapter by lazy { IncomeAdapter() }
    private val lossAdapter by lazy { LossAdapter() }
    private val targetAdapter by lazy { TargetAdapter() }
    private val yearIncomeAdapter by lazy { YearIncomeAdapter() }

    private val currentDate = Date(System.currentTimeMillis())
    private var selectedDate = Date(currentDate.year, currentDate.month, 1)
    private var selectedYear = Date(currentDate.year, 1, 1)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFinanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValueFromArgs()
        setupViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        with(viewModel) {
            if (projectId != Const.UNDEFINED_ID) {
                getProjectItem(projectId)
                getData(args.projectId, selectedDate, selectedYear)
            }
            state.observe(viewLifecycleOwner) {
                when (it) {
                    is FinanceState.TargetListError -> {
                        with(binding) {
                            rvTarget.visibility = View.GONE
                            inclErrorTarget.clError.visibility = View.VISIBLE
                        }
                    }

                    is FinanceState.IncomeItemError -> {
                        with(binding) {
                            clIncomeContent.visibility = View.GONE
                            inclErrorIncome.clError.visibility = View.VISIBLE
                        }
                    }

                    is FinanceState.YearIncomeItemError -> {
                        with(binding) {
                            clYearIncomeContent.visibility = View.GONE
                            inclErrorYearIncome.clError.visibility = View.VISIBLE
                        }
                    }

                    is FinanceState.LossItemError -> {
                        with(binding) {
                            clLossContent.visibility = View.GONE
                            inclErrorLoss.clError.visibility = View.VISIBLE
                        }
                    }

                    is FinanceState.ProjectItem -> {
                        switchScreensAdding()
                        binding.apply {
                            tvProjectFinance.text = it.projectItem.name
                        }
                    }

                    is FinanceState.LossList -> {
                        var totalLoss = 0
                        it.lossList.forEach { transaction ->
                            totalLoss += transaction.count
                        }
                        with(binding) {
                            tvLossProject.text = formatPrice(totalLoss)
                            lossAdapter.submitList(it.lossList)
                            clLossContent.visibility = View.VISIBLE
                            inclErrorLoss.clError.visibility = View.GONE
                        }
                    }

                    is FinanceState.IncomeList -> {
                        var totalIncome = 0
                        it.incomeList.forEach { transaction ->
                            totalIncome += transaction.count
                        }
                        with(binding) {
                            tvIncomeProject.text = formatPrice(totalIncome)
                            incomeAdapter.submitList(it.incomeList)
                            clIncomeContent.visibility = View.VISIBLE
                            inclErrorIncome.clError.visibility = View.GONE
                        }
                    }

                    is FinanceState.TargetList -> {
                        targetAdapter.submitList(it.targetList)
                        with(binding) {
                            rvTarget.visibility = View.VISIBLE
                            inclErrorTarget.clError.visibility = View.GONE
                        }
                    }

                    is FinanceState.YearIncomeItem -> {
                        with(binding) {
                            clYearIncomeContent.visibility = View.VISIBLE
                            inclErrorYearIncome.clError.visibility = View.GONE
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun switchScreensAdding() {
        with(binding) {
            if (projectId != Const.UNDEFINED_ID) {
                tvChoiceProject.visibility = View.GONE
                clFinance.visibility = View.VISIBLE
            } else {
                tvChoiceProject.visibility = View.GONE
                clFinance.visibility = View.VISIBLE
            }
        }
    }

    private fun setValueFromArgs() {
        projectId = args.projectId
    }

    private fun setupViews() {
//        switchScreensAdding()
        setListenersInView()
        setDate(selectedDate)
        setRecyclerViews()
        setViewErrors()
        refreshFragment()
    }

    private fun refreshFragment() {
        with(binding.swipeRefresh) {
            isEnabled = false
            if (projectId != Const.UNDEFINED_ID) {
                isEnabled = true
            }
            setColorSchemeResources(R.color.sw_purple)
            setOnRefreshListener {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(300)
                    viewModel.getData(projectId, selectedDate, selectedYear)
                    isRefreshing = false
                }
            }
        }
    }

    private fun setViewErrors() {
        with(binding) {
            inclErrorLoss.tvNotFound.text = "Расходов за данный\nмесяц не обнаружено"
            inclErrorYearIncome.tvNotFound.text = "Cтатистики за данный\nгод не обнаружено"
            inclErrorTarget.tvNotFound.text = "Целей у проекта\nне обнаружено"
        }
    }

    private fun setRecyclerViews() {
        setIncomeAdapter()
        setLossAdapter()
        setTargetAdapter()
        setYearIncomeAdapter()
    }

    private fun setIncomeAdapter() {
        with(binding) {
            rvIncome.adapter = incomeAdapter
        }
    }

    private fun setLossAdapter() {
        with(binding) {
            rvLoss.adapter = lossAdapter
        }
    }

    private fun setTargetAdapter() {
        LinearSnapHelper().attachToRecyclerView(binding.rvTarget)
        binding.rvTarget.adapter = targetAdapter
        targetAdapter.onClickBtnMoreListener = { target, anchorView ->
            setOpenMenuInTarget(anchorView, target)
        }
    }

    private fun setOpenMenuInTarget(anchorView: View, target: TargetModel) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.menuInflater.inflate(R.menu.target_menu, popup.menu)
        popup.setForceShowIcon(true)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_add -> {
                    if (target.isFinished) {
                        CustomToast.toastDefault(requireContext(), "Цель достигнута")
                        false
                    } else {
                        openModalAddTarget(target.id)
                        true
                    }
                }

                R.id.menu_edit -> {
                    openModalEditTarget(target)
                    true
                }

                R.id.menu_delete -> {
                    openModalDeleteTarget(target)
                    true
                }

                else -> {
                    false
                }
            }
        }
        popup.show()
    }

    private fun openModalEditTarget(target: TargetModel) {
        findNavController().navigate(
            FinanceFragmentDirections
                .actionFinanceFragmentToAddTargetModal(
                    projectId = projectId,
                    screenMode = Const.MODE_EDIT_TARGET,
                    targetId = target.id
                )
        )
    }

    private fun openModalAddTarget(targetId: Int) {
        findNavController().navigate(
            FinanceFragmentDirections.actionFinanceFragmentToRefillTargetModal(
                targetId
            )
        )
    }

    private fun openModalDeleteTarget(target: TargetModel) {
        findNavController().navigate(
            FinanceFragmentDirections.actionFinanceFragmentToDeleteTargetModal(
                target
            )
        )
    }

    private fun setYearIncomeAdapter() {
        binding.rvYearIncome.adapter = yearIncomeAdapter
    }

    private fun setListenersInView() {
        with(binding) {
            if (projectId != null && projectId != Const.UNDEFINED_ID) {
                btnAddExpenses.setOnClickListener {
                    val date = Date(selectedDate.year, selectedDate.month, 1)
                    findNavController().navigate(
                        FinanceFragmentDirections.actionFinanceFragmentToAddLossModal(
                            args.projectId,
                            date.time
                        )
                    )
                }
                btnAddTarget.setOnClickListener {
                    findNavController().navigate(
                        FinanceFragmentDirections
                            .actionFinanceFragmentToAddTargetModal(
                                projectId = projectId,
                                screenMode = Const.MODE_ADD_TARGET,
                                targetId = Const.UNDEFINED_ID
                            )
                    )
                }
                clChoiceDate.setOnClickListener {
                    openMonthPicker()
                }
            }
            tvProjectFinance.setOnClickListener {
                findNavController().navigate(
                    FinanceFragmentDirections.actionFinanceFragmentToChoiceProjectModal(Const.MODE_CHOICE_PROJECT_FINANCE)
                )
            }
        }
    }

    private fun setDate(date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        setDateInView(month, year)
    }

    private fun openMonthPicker() {
        RackMonthPicker(requireContext())
            .setPositiveButton { month, startDate, endDate, year, monthLabel ->
                val selectedMonth = month - 1
                val selectedYear = year - 1900
                val date = Date(selectedYear, selectedMonth, 1)
                this.selectedDate = date
                setDate(date)
                viewModel.getData(projectId, selectedDate, this.selectedYear)
            }
            .setNegativeButton {
                it.dismiss()
            }
            .show()
    }

    private fun setDateInView(month: Int, year: Int) {
        with(binding) {
            tvDateMonth.text = month.toString()
            tvDateYear.text = year.toString()
        }
    }
//
//    private fun cleanArgs(){
//        val projectIdArgs = FinanceFragmentArgs.fromBundle(requireArguments()).projectId
//        if (projectId == projectIdArgs){
//            requireArguments().clear()
//            projectId = Const.UNDEFINED_ID
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}