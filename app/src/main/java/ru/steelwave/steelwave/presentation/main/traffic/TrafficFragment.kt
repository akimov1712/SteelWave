package ru.steelwave.steelwave.presentation.main.traffic

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kal.rackmonthpicker.RackMonthPicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.Const
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.FragmentTrafficBinding
import java.sql.Date
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class TrafficFragment : Fragment() {


    private val args by navArgs<TrafficFragmentArgs>()
    private var projectId = Const.UNDEFINED_ID

    private var _binding: FragmentTrafficBinding? = null
    private val binding: FragmentTrafficBinding
        get() = _binding ?: throw RuntimeException("FragmentTrafficBinding == null")


    private val viewModel by viewModels<TrafficViewModel>()

    private val currentDate = Date(System.currentTimeMillis())
    private var selectedDate = Date(currentDate.year, currentDate.month, 1)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTrafficBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValueFromArgs()
        setupViews()
        observeViewModel()
    }

    private fun setupViews(){
        refreshFragment()
        setListenersInView()
        setViewErrors()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            with(viewModel) {
                if (projectId != Const.UNDEFINED_ID) {
                    getProjectItem(projectId)
                }
                state.collect{
                    when(it){
                        is TrafficState.ProjectItem -> {
                            switchScreensAdding()
                            binding.apply {
                                tvProjectTrafic.text = it.projectItem.name
                            }
                        }
                        is TrafficState.VisitionItem -> {

                        }
                        is TrafficState.TransferList -> {

                        }
                        is TrafficState.Loading -> {}
                    }
                }
            }
        }
    }

    private fun setValueFromArgs() {
        projectId = args.projectId
    }

    private fun setViewErrors(){
        with(binding){
            inclErrorVisition.tvNotFound.text = "Посетителей за данный\nмесяц не обнаружено"
        }
    }

    private fun refreshFragment(){
        with(binding.swipeRefresh){
            setColorSchemeResources(R.color.sw_purple)
            setOnRefreshListener {
                CoroutineScope(Dispatchers.IO).launch{
                    delay(300)
                    isRefreshing = false
                }
            }
        }
    }

    private fun setListenersInView() {
        with(binding) {
            if (projectId != null && projectId != Const.UNDEFINED_ID){
                clChoiceDateInTotalVisits.setOnClickListener {
                    openMonthPicker()
                }
            }
            tvProjectTrafic.setOnClickListener {
                findNavController().navigate(
                    TrafficFragmentDirections.actionTraficFragmentToChoiceProjectModal(
                        Const.MODE_CHOICE_PROJECT_TRAFFIC
                    )
                )
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
//                viewModel.getData(projectId, selectedDate, this.selectedYear)
            }
            .setNegativeButton {
                it.dismiss()
            }
            .show()
    }

    private fun switchScreensAdding() {
        with(binding){
            if (projectId != Const.UNDEFINED_ID){
                tvChoiceProject.visibility = View.GONE
                clTraffic.visibility = View.VISIBLE
            } else {
                tvChoiceProject.visibility = View.VISIBLE
                clTraffic.visibility = View.GONE
            }
        }
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

}