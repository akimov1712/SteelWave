package ru.steelwave.steelwave.presentation.main.finance

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import ru.steelwave.steelwave.databinding.ModalControlLossBinding

class AddLossModal: DialogFragment() {

    private var _binding: ModalControlLossBinding? = null
    private val binding: ModalControlLossBinding
    get() = _binding ?: throw RuntimeException("ModalControlLossBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ModalControlLossBinding.inflate(inflater, container, false)
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
            btnAdd.setOnClickListener {
                Toast.makeText(requireContext(), "Расход добавлена", Toast.LENGTH_SHORT).show()
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