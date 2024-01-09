package ru.steelwave.steelwave.presentation.main.finance.modals

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
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ModalConfirmDeleteTargetBinding
import ru.steelwave.steelwave.presentation.main.finance.FinanceState
import ru.steelwave.steelwave.presentation.main.finance.FinanceViewModel

@AndroidEntryPoint
class DeleteTargetModal : DialogFragment() {

    private val args by navArgs<DeleteTargetModalArgs>()

    private var _binding: ModalConfirmDeleteTargetBinding? = null
    private val binding: ModalConfirmDeleteTargetBinding
        get() = _binding ?: throw RuntimeException("ModalConfirmDeleteTargetBinding == null")

    private val viewModel by viewModels<FinanceViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ModalConfirmDeleteTargetBinding.inflate(inflater, container, false)
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
                    is FinanceState.ShouldCloseDeleteTargetModal -> {
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
                "${resources.getString(R.string.you_really_need_delete_target)} ${args.target.name}"
            val spannableString = SpannableString(text)
            val startIndex = text.indexOf(args.target.name)
            spannableString.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.sw_black_transparent)),
                0,
                startIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.sw_purple)),
                startIndex,
                startIndex + args.target.name.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvDeleteTarget.text = spannableString

        }
    }

    private fun setListenersInView() {
        with(binding) {
            btnDelete.setOnClickListener {
                viewModel.deleteTarget(args.target.id)
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