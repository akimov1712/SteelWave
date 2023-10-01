package ru.steelwave.steelwave.presentation.main.employees.modals

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import ru.steelwave.steelwave.databinding.ModalAddEmployeeBinding
import ru.steelwave.steelwave.presentation.CustomToast

class AddEmployeeModal: DialogFragment() {

    private var _binding: ModalAddEmployeeBinding? = null
    private val binding: ModalAddEmployeeBinding
    get() = _binding ?: throw RuntimeException("ModalAddEmployeeBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ModalAddEmployeeBinding.inflate(inflater, container, false)
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
            btnContinue.setOnClickListener {
                if (clPersonalData.isVisible){
                    clPersonalData.visibility = View.GONE
                    clPosition.visibility = View.VISIBLE
                } else {
                    clPosition.visibility = View.GONE
                    clRegistration.visibility = View.VISIBLE
                    btnContinue.visibility = View.GONE
                    btnAdd.visibility = View.VISIBLE
                }
            }
            btnAdd.setOnClickListener{
                CustomToast.toastDefault(requireContext(), "Пользователь добавлен")
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