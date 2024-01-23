package ru.steelwave.steelwave.presentation.main.finance.modals

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
import ru.steelwave.steelwave.Const
import ru.steelwave.steelwave.databinding.ModalAddTargetBinding
import ru.steelwave.steelwave.presentation.main.finance.FinanceState
import ru.steelwave.steelwave.presentation.main.finance.FinanceViewModel

@AndroidEntryPoint
class AddTargetModal : DialogFragment() {


    private val args by navArgs<AddTargetModalArgs>()

    private var _binding: ModalAddTargetBinding? = null
    private val binding: ModalAddTargetBinding
        get() = _binding ?: throw RuntimeException("ModalAddTargetBinding == null")

    private val viewModel by viewModels<FinanceViewModel>()

    private var screenMode: String = Const.MODE_UNKNOWN

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ModalAddTargetBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        setupViews()
        choiceMode()
        observeViewModel()
    }

    private fun parseArgs() {
        screenMode = args.screenMode
    }

    private fun choiceMode() {
        when (screenMode) {
            Const.MODE_ADD_TARGET -> runModeAdd()
            Const.MODE_EDIT_TARGET -> runModeEdit()
        }
    }

    private fun runModeEdit() {
        with(binding) {
            var targetId: Int? = null
            viewModel.getTargetItem(args.targetId)
            viewModel.state.observe(viewLifecycleOwner) {
                when (it) {
                    is FinanceState.TargetItem -> {
                        targetId = it.targetItem.id
                        etNameTarget.setText(it.targetItem.name)
                        etStartPrice.setText(it.targetItem.collectedPrice.toString())
                        etEndPrice.setText(it.targetItem.totalPrice.toString())
                    }

                    else -> {}
                }
            }
            btnAdd.setOnClickListener {
                val nameTarget = etNameTarget.text.toString()
                val startPrice = etStartPrice.text.toString()
                val endPrice = etEndPrice.text.toString()
                targetId?.let {
                    viewModel.updateTarget(
                        it,
                        nameTarget,
                        startPrice,
                        endPrice
                    )
                }
            }
        }
    }

    private fun runModeAdd() {
        with(binding) {
            btnAdd.setOnClickListener {
                val nameTarget = etNameTarget.text.toString()
                val startPrice = etStartPrice.text.toString()
                val endPrice = etEndPrice.text.toString()
                viewModel.addTarget(
                    args.projectId,
                    nameTarget,
                    startPrice,
                    endPrice
                )
            }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            state.observe(viewLifecycleOwner) {
                when (it) {
                    is FinanceState.ShouldCloseAddTargetModal -> {
                        dismiss()
                    }

                    is FinanceState.ErrorInputNameAddTarget -> {
                        binding.etNameTarget.error = "Проверьте поле."
                    }

                    is FinanceState.ErrorInputEndPrice -> {
                        binding.etEndPrice.error = "Число не может быть меньше нуля."
                    }

                    is FinanceState.ErrorInputStartPrice -> {
                        binding.etStartPrice.error =
                            "Число не может быть меньше нуля и больше конечной ставки."
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
            btnCancel.setOnClickListener {
                dismiss()
            }
            etStartPrice.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus && etStartPrice.text.toString() == "0") {
                    etStartPrice.text.clear()
                } else {
                    if (etStartPrice.text.toString() == "") {
                        etStartPrice.setText("0")
                    }
                }
            }
            etEndPrice.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus && etEndPrice.text.toString() == "0") {
                    etEndPrice.text.clear()
                } else {
                    if (etEndPrice.text.toString() == "") {
                        etEndPrice.setText("0")
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