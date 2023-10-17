package ru.steelwave.steelwave.presentation.main.employees.modals

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ModalChangePositionBinding
import ru.steelwave.steelwave.presentation.base.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.employees.EmployeesState
import ru.steelwave.steelwave.presentation.main.employees.EmployeesViewModel
import javax.inject.Inject

class ChangePositionModal : DialogFragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }
    private val args by navArgs<ChangePositionModalArgs>()

    private var _binding: ModalChangePositionBinding? = null
    private val binding: ModalChangePositionBinding
        get() = _binding ?: throw RuntimeException("ModalChangePositionBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[EmployeesViewModel::class.java]
    }

    override fun onAttach(activity: Activity) {
        component.inject(this)
        super.onAttach(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ModalChangePositionBinding.inflate(inflater, container, false)
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
        setAdapterInDropMenu()
    }

    private fun observeViewModel() {
        with(viewModel) {
            with(binding) {
                state.observe(viewLifecycleOwner) {
                    when (it) {
                        is EmployeesState.ShouldCloseModal -> {
                            dismiss()
                        }
                        is EmployeesState.ErrorInputPosition -> {
                        etPosition.error = getString(R.string.field_not_can_empty)
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
                val inputPosition = etPosition.text.trim().toString()
                viewModel.changePosition(args.userId,inputPosition)
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun setAdapterInDropMenu(){
        val suggestions = requireContext().resources.getStringArray(R.array.proffesionArray)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_menu, suggestions)
        binding.etPosition.setAdapter(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}