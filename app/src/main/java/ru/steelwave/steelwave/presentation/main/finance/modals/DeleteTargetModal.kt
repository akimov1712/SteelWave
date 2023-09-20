package ru.steelwave.steelwave.presentation.main.finance.modals

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.Consts
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ModalConfirmDeleteTargetBinding
import ru.steelwave.steelwave.presentation.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.finance.FinanceViewModel
import javax.inject.Inject

class DeleteTargetModal : DialogFragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private val args by navArgs<DeleteTargetModalArgs>()

    private var _binding: ModalConfirmDeleteTargetBinding? = null
    private val binding: ModalConfirmDeleteTargetBinding
        get() = _binding ?: throw RuntimeException("ModalConfirmDeleteTargetBinding == null")

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
        _binding = ModalConfirmDeleteTargetBinding.inflate(inflater, container, false)
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
            shouldCloseDeleteTargetModal.observe(viewLifecycleOwner){
                dismiss()
            }
        }
    }

    private fun setupViews() {
        setListenersInView()
        setTextConfirm()
    }

    private fun setTextConfirm(){
        with(binding){
            val text = "${resources.getString(R.string.you_really_need_delete_target)} ${args.target.name}"
            val spannableString = SpannableString(text)
            val startIndex = text.indexOf(args.target.name)
            spannableString.setSpan(ForegroundColorSpan(resources.getColor(R.color.sw_black_transparent)), 0, startIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(ForegroundColorSpan(resources.getColor(R.color.sw_purple)), startIndex, startIndex + args.target.name.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            tvDeleteTarget.text = spannableString

        }
    }

    private fun setListenersInView() {
        with(binding) {
            btnDelete.setOnClickListener{
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