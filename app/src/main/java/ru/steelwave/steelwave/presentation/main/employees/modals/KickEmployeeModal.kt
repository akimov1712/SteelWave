package ru.steelwave.steelwave.presentation.main.employees.modals

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ModalConfirmKickEmployeeBinding
import ru.steelwave.steelwave.presentation.main.employees.EmployeesState
import ru.steelwave.steelwave.presentation.main.employees.EmployeesViewModel
import ru.steelwave.steelwave.utils.formatName
import javax.inject.Inject

class KickEmployeeModal : DialogFragment() {

    private val args by navArgs<KickEmployeeModalArgs>()

    private var _binding: ModalConfirmKickEmployeeBinding? = null
    private val binding: ModalConfirmKickEmployeeBinding
        get() = _binding ?: throw RuntimeException("ModalConfirmKickEmployeeBinding == null")

    private val viewModel by viewModels<EmployeesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ModalConfirmKickEmployeeBinding.inflate(inflater, container, false)
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
            state.observe(viewLifecycleOwner) {
                when (it) {
                    is EmployeesState.ShouldCloseModal -> {
                        dismiss()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setupViews() {
        setListenersInView()
        setTextConfirm()
    }

    private fun setTextConfirm() {
        with(binding) {
            val text =
                "${resources.getString(R.string.you_really_need_delete_target)} ${formatName(args.user)}"
            val spannableString = SpannableString(text)
            val startIndex = text.indexOf(formatName(args.user))
            spannableString.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.sw_black_transparent)),
                0,
                startIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.sw_purple)),
                startIndex,
                startIndex + formatName(args.user).length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvDeleteTarget.text = spannableString

        }
    }

    private fun setListenersInView() {
        with(binding) {
            btnDelete.setOnClickListener {
                viewModel.deleteEmployee(args.user.id)
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