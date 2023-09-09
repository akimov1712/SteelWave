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
import com.google.android.material.datepicker.MaterialDatePicker
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.databinding.FragmentFinanceBinding
import ru.steelwave.steelwave.presentation.ViewModelFactory
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

    private var dateLong = System.currentTimeMillis()

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
        setDate(dateLong)
    }

    private fun setListenersInView() {
        with(binding) {
            btnAddExpenses.setOnClickListener {
                AddLossModal().also {
                    it.show(parentFragmentManager, TAG_DIALOG_ADD_LOSS)
                }
            }
            tvProjectFinance.setOnClickListener {
                findNavController().navigate(FinanceFragmentDirections.actionFinanceFragmentToChoiceProjectModal())
            }
        }
    }

    private fun setDate(date: Long){
        val date = Date(date)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        setDateInView(month, year)
    }

    private fun openDatePicker(){
        val picker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setTitleText("Выберите месяц, за который хотите посмотреть доходы и расходы")
            .build()
        picker.show(childFragmentManager, TAG_DIALOG_DATE_PICKER)

        picker.addOnPositiveButtonClickListener{ selectedDate ->
            dateLong = selectedDate
            setDate(dateLong)
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

    companion object {
        private const val TAG_DIALOG_ADD_LOSS = "tag_dialog_add_loss"
        private const val UNDEFINED_ID = -1
        private const val TAG_DIALOG_DATE_PICKER = "tag_dialog_date_picker"
    }

}