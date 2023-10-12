package ru.steelwave.steelwave.presentation.main.finance.modals

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.databinding.ModalRefillTargetBinding
import ru.steelwave.steelwave.presentation.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.finance.FinanceState
import ru.steelwave.steelwave.presentation.main.finance.FinanceViewModel
import javax.inject.Inject

class RefillTargetModal : DialogFragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private val args by navArgs<RefillTargetModalArgs>()

    private var _binding: ModalRefillTargetBinding? = null
    private val binding: ModalRefillTargetBinding
        get() = _binding ?: throw RuntimeException("ModalRefillTargetBinding == null")

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
        _binding = ModalRefillTargetBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        with(viewModel) {
            state.observe(viewLifecycleOwner){
                when(it){
                    is FinanceState.ShouldCloseRefillTargetModal -> {
                        dismiss()
                    }
                    is FinanceState.ErrorInputRefill -> {
                        binding.etCount.error = "Проверьте поле"
                    } else -> {}
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
                val inputCount = etCount.text.toString()
                viewModel.refillTarget(args.targetId, inputCount)
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
            etCount.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus && etCount.text.toString() == "0") {
                    etCount.text.clear()
                } else {
                    if (etCount.text.toString() == ""){
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