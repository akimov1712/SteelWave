package ru.steelwave.steelwave.presentation.main.finance.modals

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.Consts
import ru.steelwave.steelwave.databinding.ModalAddTargetBinding
import ru.steelwave.steelwave.presentation.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.finance.FinanceViewModel
import javax.inject.Inject

class AddTargetModal : DialogFragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private val args by navArgs<AddTargetModalArgs>()

    private var _binding: ModalAddTargetBinding? = null
    private val binding: ModalAddTargetBinding
        get() = _binding ?: throw RuntimeException("ModalAddTargetBinding == null")

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
        _binding = ModalAddTargetBinding.inflate(inflater, container, false)
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
            shouldCloseAddTargetModal.observe(viewLifecycleOwner){
                dismiss()
            }
            errorInputNameAddTarget.observe(viewLifecycleOwner){
                binding.etNameTarget.error = "Проверьте поле."
            }
            errorInputEndPrice.observe(viewLifecycleOwner) {
                binding.etEndPrice.error = "Число не может быть меньше нуля."
            }
            errorInputStartPrice.observe(viewLifecycleOwner) {
                binding.etStartPrice.error = "Число не может быть меньше нуля и больше конечной ставки."
            }
        }
    }

    private fun setupViews() {
        setListenersInView()
    }

    private fun setListenersInView() {
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