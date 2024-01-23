package ru.steelwave.steelwave.presentation.main.employees.modals

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.steelwave.steelwave.databinding.ModalChangeSalaryBinding
import ru.steelwave.steelwave.presentation.main.employees.EmployeesState
import ru.steelwave.steelwave.presentation.main.employees.EmployeesViewModel

@AndroidEntryPoint
class ChangeSalaryModal : DialogFragment() {

    private val args by navArgs<ChangeSalaryModalArgs>()

    private var _binding: ModalChangeSalaryBinding? = null
    private val binding: ModalChangeSalaryBinding
        get() = _binding ?: throw RuntimeException("ModalChangeSalaryBinding == null")


    private val viewModel by viewModels<EmployeesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ModalChangeSalaryBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        setListenersInView()
    }

    private fun observeViewModel() {
        with(viewModel) {
            with(binding) {
                state.observe(viewLifecycleOwner) {
                    when (it) {
                        is EmployeesState.ShouldCloseModal -> {
                            dismiss()
                        }

                        is EmployeesState.ErrorInputSalary -> {
                            etCount.error = "Зарплата не может быть нулем"
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun setListenersInView() {
        with(binding) {
            btnAdd.setOnClickListener {
                val inputSalary = etCount.text.trim().toString()
                viewModel.changeSalary(args.userId, inputSalary)
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
            etCount.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus && etCount.text.toString() == "0") {
                    etCount.text.clear()
                } else {
                    if (etCount.text.toString() == "") {
                        etCount.setText("0")
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}