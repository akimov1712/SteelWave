package ru.steelwave.steelwave.presentation.main.finance

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearSnapHelper
import com.kal.rackmonthpicker.RackMonthPicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.Consts
import ru.steelwave.steelwave.Loger
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.FragmentFinanceBinding
import ru.steelwave.steelwave.presentation.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.finance.adapters.incomeAdapter.IncomeAdapter
import ru.steelwave.steelwave.presentation.main.finance.adapters.lossAdapter.LossAdapter
import ru.steelwave.steelwave.presentation.main.finance.adapters.targetAdapter.TargetAdapter
import ru.steelwave.steelwave.presentation.main.finance.adapters.yearIncomeAdapter.YearIncomeAdapter
import ru.steelwave.steelwave.utils.formatPrice
import java.sql.Date
import java.util.Calendar
import javax.inject.Inject


class FinanceFragment : Fragment(){

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

    private val currentDate = Date(System.currentTimeMillis())
    private var selectedDate = Date(currentDate.year, currentDate.month, 1)
    private var selectedYear = Date(currentDate.year, 1, 1)

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
                getData(args.projectId, selectedDate, selectedYear)
            }
            projectItem.observe(viewLifecycleOwner) {
                switchScreensAdding()
                binding.apply {
                    tvProjectFinance.text = it.name
                }
            }
            incomeList.observe(viewLifecycleOwner){
                var totalIncome = 0
                it.forEach { transaction ->
                    totalIncome += transaction.count
                }
                with(binding){
                    tvIncomeProject.text = formatPrice(totalIncome)
                    incomeAdapter.submitList(it)
                    clIncomeContent.visibility = View.VISIBLE
                    inclErrorIncome.clError.visibility = View.GONE
                }
            }
            lossList.observe(viewLifecycleOwner){
                var totalLoss = 0
                it.forEach { transaction ->
                    totalLoss += transaction.count
                }
                with(binding){
                    tvLossProject.text = formatPrice(totalLoss)
                    lossAdapter.submitList(it)
                    clLossContent.visibility = View.VISIBLE
                    inclErrorLoss.clError.visibility = View.GONE
                }
            }
            targetList.observe(viewLifecycleOwner){
                targetAdapter.submitList(it)
            }
            yearIncomeItem.observe(viewLifecycleOwner){
                with(binding){
                    clYearIncomeContent.visibility = View.VISIBLE
                    inclErrorYearIncome.clError.visibility = View.GONE
                }
            }
            incomeItemError.observe(viewLifecycleOwner){
                with(binding){
                    clIncomeContent.visibility = View.GONE
                    inclErrorIncome.clError.visibility = View.VISIBLE
                }
            }
            yearIncomeItemError.observe(viewLifecycleOwner){
                with(binding){
                    clYearIncomeContent.visibility = View.GONE
                    inclErrorYearIncome.clError.visibility = View.VISIBLE
                }

            }
            lossItemError.observe(viewLifecycleOwner){
                with(binding){
                    clLossContent.visibility = View.GONE
                    inclErrorLoss.clError.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun switchScreensAdding() {
        with(binding){
            if (projectId != UNDEFINED_ID){
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

    private fun refreshFragment(){
        with(binding.swipeRefresh){
            isEnabled = false
            if (projectId != UNDEFINED_ID){
                isEnabled = true
            }
            setColorSchemeResources(R.color.sw_purple)
            setOnRefreshListener {
                CoroutineScope(Dispatchers.IO).launch{
                    delay(300)
                    viewModel.getData(projectId, selectedDate, selectedYear)
                    isRefreshing = false
                }
            }
        }
    }

    private fun setViewErrors(){
        with(binding){
            inclErrorLoss.tvNotFound.text = "Расходов за данный\nмесяц не обнаружено"
            inclErrorYearIncome.tvNotFound.text = "Cтатистики за данный\nгод не обнаружено"
        }
    }

    private fun setRecyclerViews(){
        setIncomeAdapter()
        setLossAdapter()
        setTargetAdapter()
        setYearIncomeAdapter()
    }

    private fun setIncomeAdapter(){
        with(binding){
            rvIncome.adapter =  incomeAdapter
        }
    }

    private fun setLossAdapter(){
        with(binding){
            rvLoss.adapter =  lossAdapter
        }
    }

    private fun setTargetAdapter(){
        LinearSnapHelper().attachToRecyclerView(binding.rvTarget)
        binding.rvTarget.adapter = targetAdapter
        targetAdapter.onClickBtnMoreListener = { targetId, anchorView ->
            setOpenMenuInTarget(anchorView, targetId)
        }
    }

    private fun setOpenMenuInTarget(anchorView: View, targetId: Int) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.menuInflater.inflate(R.menu.target_menu, popup.menu)
        popup.setForceShowIcon(true)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_edit -> {
                    openModalEditTarget(targetId)
                    true
                }

                R.id.menu_delete -> {
                    openModalDeleteTarget(targetId)
                    true
                }
                else -> {
                    false
                }
            }
        }
        popup.show()
    }

    private fun openModalEditTarget(targetId: Int){

    }

    private fun openModalDeleteTarget(targetId: Int){

    }

    private fun setYearIncomeAdapter(){
        binding.rvYearIncome.adapter = yearIncomeAdapter
    }

    private fun setListenersInView() {
        with(binding) {
            if (projectId != null && projectId != UNDEFINED_ID){
                btnAddExpenses.setOnClickListener {
                    val date = Date(selectedDate.year, selectedDate.month, 1)
                    findNavController().navigate(
                        FinanceFragmentDirections.actionFinanceFragmentToAddLossModal(
                            args.projectId,
                            date.time
                        )
                    )
                }
                btnAddTarget.setOnClickListener{
                    findNavController().navigate(FinanceFragmentDirections.actionFinanceFragmentToAddTargetModal(projectId))
                }
                clChoiceDate.setOnClickListener {
                    openMonthPicker()
                }
            }
            tvProjectFinance.setOnClickListener {
                findNavController().navigate(FinanceFragmentDirections.actionFinanceFragmentToChoiceProjectModal())
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
                viewModel.getData(projectId, selectedDate, this.selectedYear)
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
//
//    private fun cleanArgs(){
//        val projectIdArgs = FinanceFragmentArgs.fromBundle(requireArguments()).projectId
//        if (projectId == projectIdArgs){
//            requireArguments().clear()
//            projectId = UNDEFINED_ID
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val UNDEFINED_ID = -1
    }

}