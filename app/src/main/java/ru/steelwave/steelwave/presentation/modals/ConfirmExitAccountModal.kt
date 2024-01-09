package ru.steelwave.steelwave.presentation.modals

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.steelwave.steelwave.databinding.ModalConfirmExitFromAccountBinding
import ru.steelwave.steelwave.presentation.AuthActivity

@AndroidEntryPoint
class ConfirmExitAccountModal: DialogFragment() {

    private var _binding: ModalConfirmExitFromAccountBinding? = null
    private val binding: ModalConfirmExitFromAccountBinding
    get() = _binding ?: throw RuntimeException("ModalConfirmExitFromAccountBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ModalConfirmExitFromAccountBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews(){
        setListenersInView()
    }

    private fun setListenersInView(){
        with(binding){
            btnExit.setOnClickListener {
                Intent(requireActivity(), AuthActivity::class.java).also {
                    startActivity(it)
                    requireActivity().finish()
                }
                dismiss()
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