package ru.steelwave.steelwave.presentation.main.finance

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearSnapHelper
import com.kal.rackmonthpicker.RackMonthPicker
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.databinding.FragmentFinanceBinding
import ru.steelwave.steelwave.domain.entity.finance.IncomeModel
import ru.steelwave.steelwave.domain.entity.finance.LossModel
import ru.steelwave.steelwave.domain.entity.finance.TargetModel
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import ru.steelwave.steelwave.domain.entity.finance.YearIncomeModel
import ru.steelwave.steelwave.presentation.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.finance.adapters.incomeAdapter.IncomeAdapter
import ru.steelwave.steelwave.presentation.main.finance.adapters.lossAdapter.LossAdapter
import ru.steelwave.steelwave.presentation.main.finance.adapters.targetAdapter.TargetAdapter
import ru.steelwave.steelwave.presentation.main.finance.adapters.yearIncomeAdapter.YearIncomeAdapter
import ru.steelwave.steelwave.presentation.main.finance.modals.AddLossModal
import ru.steelwave.steelwave.utils.formatNumber
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


class FinanceFragment : Fragment() {

    private val args by navArgs<FinanceFragmentArgs>()
    private var projectId = UNDEFINED_ID

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private var _binding: FragmentFinanceBinding? = null
    private val binding: FragmentFinanceBinding
        get() = _binding ?: throw RuntimeException("FragmentFinanceBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FinanceViewModel::class.java]
    }

    private val incomeAdapter by lazy{ IncomeAdapter() }
    private val lossAdapter by lazy{ LossAdapter() }
    private val targetAdapter by lazy{ TargetAdapter() }
    private val yearIncomeAdapter by lazy{ YearIncomeAdapter() }

    private var selectedDate = Date(System.currentTimeMillis())
    private var selectedYear = Date(System.currentTimeMillis())

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

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
            if (projectId != UNDEFINED_ID) {
                getProjectItem(projectId)
            }
            projectItem.observe(viewLifecycleOwner) {
                switchScreensAdding()
                binding.apply {
                    tvProjectFinance.text = it.name
                }
            }
        }
    }

    private fun switchScreensAdding() {
        binding.tvChoiceProject.visibility = View.GONE
        binding.clFinance.visibility = View.VISIBLE
    }

    private fun setValueFromArgs() {
        projectId = args.projectId
    }

    private fun setupViews() {
        setListenersInView()
        setDate(selectedDate)
        setRecyclerViews()
    }

    private fun setRecyclerViews(){
        setIncomeAdapter()
        setLossAdapter()
        setTargetAdapter()
        setYearIncomeAdapter()
    }

    private fun setIncomeAdapter(){
        val transactionList = mutableListOf<TransactionModel>()
        transactionList.add(TransactionModel("Доход от рекламы", 50000))
        transactionList.add(TransactionModel("Доход от посетителей", 25000))
        transactionList.add(TransactionModel("Доход от партнерки", 60000))
        transactionList.add(TransactionModel("Выручка проекта", 15000))
        transactionList.add(TransactionModel("Маржинальная прибыль", 30000))
        transactionList.add(TransactionModel("Чистая прибыль", 20000))
        val incomeModel = IncomeModel(1, 3, 149643694, 72, transactionList)
        incomeAdapter.submitList(incomeModel.detailedIncome)

        var totalLoss = 0
        transactionList.forEach {
            totalLoss += it.count
        }

        with(binding){
            binding.rvIncome.adapter =  incomeAdapter
            tvIncomeProject.text = "$" + formatNumber(totalLoss)
            tvProjectProfitability.text = incomeModel.projectProfit.toString() + "%"
        }
    }

    private fun setLossAdapter(){
        val transactionList = mutableListOf<TransactionModel>()
        transactionList.add(TransactionModel("Оплата хостинга", 80))
        transactionList.add(TransactionModel("Реклама в блоге", 750))
        transactionList.add(TransactionModel("ЗП - FrontEnd специалист", 5830))
        transactionList.add(TransactionModel("ЗП - BackEnd специалист", 5830))
        transactionList.add(TransactionModel("Оплата хостинга", 100))
        transactionList.add(TransactionModel("Обслуживание офиса", 1000))
        val incomeModel = LossModel(1, 3, 149643694, transactionList)
        lossAdapter.submitList(incomeModel.detailedIncome)

        var totalIncome = 0
        transactionList.forEach {
            totalIncome += it.count
        }

        with(binding){
            binding.rvLoss.adapter =  lossAdapter
            tvLossProject.text = "$" + formatNumber(totalIncome)
        }
    }

    private fun setTargetAdapter(){
        LinearSnapHelper().attachToRecyclerView(binding.rvTarget)
        binding.rvTarget.adapter = targetAdapter
    }

    private fun setYearIncomeAdapter(){
        val yearIncomeModel = YearIncomeModel(1, 2, 2023)
        yearIncomeAdapter.submitList(yearIncomeModel.yearIncomeList)
        binding.rvYearIncome.adapter = yearIncomeAdapter
    }

    private fun setListenersInView() {
        with(binding) {
            btnAddExpenses.setOnClickListener {
                findNavController().navigate(FinanceFragmentDirections.actionFinanceFragmentToAddLossModal(args.projectId))
            }
            tvProjectFinance.setOnClickListener {
                findNavController().navigate(FinanceFragmentDirections.actionFinanceFragmentToChoiceProjectModal())
            }
            clChoiceDate.setOnClickListener {
                openMonthPicker()
            }
        }
    }

    private fun setDate(date: Date){
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
            }
            .setNegativeButton {
                it.dismiss()
            }
            .show()
    }

    private fun setDateInView(month: Int, year: Int){
        with(binding){
            tvDateMonth.text = month.toString()
            tvDateYear.text = year.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG_DIALOG_ADD_LOSS = "tag_dialog_add_loss"
        private const val UNDEFINED_ID = -1
        private const val TAG_DIALOG_DATE_PICKER = "tag_dialog_date_picker"
    }

}