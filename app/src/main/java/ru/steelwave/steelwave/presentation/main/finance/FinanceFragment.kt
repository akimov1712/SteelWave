package ru.steelwave.steelwave.presentation.main.finance

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.FragmentFinanceBinding
import ru.steelwave.steelwave.presentation.main.project.AddProjectModal

class FinanceFragment : Fragment() {

    private var _binding: FragmentFinanceBinding? = null
    private val binding: FragmentFinanceBinding
        get() = _binding ?: throw RuntimeException("FragmentFinanceBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFinanceBinding.inflate(inflater, container, false)
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
           btnAddExpenses.setOnClickListener {
                AddLossModal().also {
                    it.show(parentFragmentManager, TAG_DIALOG_ADD_LOSS)
                }
           }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val TAG_DIALOG_ADD_LOSS = "tag_dialog_add_loss"
    }

}