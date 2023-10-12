package ru.steelwave.steelwave.presentation.main.finance.modals

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.databinding.ModalControlLossBinding
import ru.steelwave.steelwave.presentation.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.finance.FinanceState
import ru.steelwave.steelwave.presentation.main.finance.FinanceViewModel
import javax.inject.Inject

class AddLossModal : DialogFragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private val args by navArgs<AddLossModalArgs>()

    private var _binding: ModalControlLossBinding? = null
    private val binding: ModalControlLossBinding
        get() = _binding ?: throw RuntimeException("ModalControlLossBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FinanceViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ModalControlLossBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun observeViewModel(){
        with(viewModel){
            state.observe(viewLifecycleOwner){
                when(it){
                    is FinanceState.ErrorInputNameAddLoss -> {
                        binding.etExpenses.error = "Введите название расхода"
                    }
                    is FinanceState.ErrorInputCount -> {
                        binding.etSumExpenses.error = "Введите сумму расхода"
                    }
                    is FinanceState.ShouldCloseAddLossModal -> {
                        dismiss()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupViews() {
        setListenersInView()
    }

    private fun setListenersInView() {
        with(binding) {
            btnAdd.setOnClickListener {
                val name = etExpenses.text.toString()
                val count = etSumExpenses.text.toString()
                viewModel.addTransaction(
                    projectId = args.projectId,
                    inputDate = args.date,
                    inputName = name,
                    inputCount = count,
                    isIncome = false
                )
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}