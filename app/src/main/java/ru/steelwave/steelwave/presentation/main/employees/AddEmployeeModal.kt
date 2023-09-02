package ru.steelwave.steelwave.presentation.main.employees

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import ru.steelwave.steelwave.databinding.ModalAddEmployeeBinding

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
                clRegistration.visibility = View.GONE
                clPersonalData.visibility = View.VISIBLE
                btnContinue.visibility = View.GONE
                btnAdd.visibility = View.VISIBLE
            }
            btnAdd.setOnClickListener{
                Toast.makeText(requireContext(), "Пользователь добавлен", Toast.LENGTH_SHORT).show()
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