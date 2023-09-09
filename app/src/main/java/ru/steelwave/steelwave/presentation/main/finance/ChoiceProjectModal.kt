package ru.steelwave.steelwave.presentation.main.finance

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ModalChoiceProjectBinding
import ru.steelwave.steelwave.presentation.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.finance.choiceProjectAdapter.ChoiceProjectAdapter
import ru.steelwave.steelwave.presentation.main.project.ProjectViewModel
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


class ChoiceProjectModal : DialogFragment() {

    private val component by lazy{
        (requireActivity().application as App).component
    }

    private var _binding: ModalChoiceProjectBinding? = null
    private val binding: ModalChoiceProjectBinding
        get() = _binding ?: throw RuntimeException("ModalChoiceProjectBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy{
        ViewModelProvider(this,viewModelFactory)[FinanceViewModel::class.java]
    }

    private val choiceProjectAdapter by lazy {
        ChoiceProjectAdapter()
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
        _binding = ModalChoiceProjectBinding.inflate(inflater, container, false)
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
            projectList.observe(viewLifecycleOwner){
                choiceProjectAdapter.submitList(it)
            }
        }
    }

    private fun setupViews(){
        setRecyclerView()
    }

    private fun setRecyclerView(){
        setChoiceProjectAdapter()
    }

    private fun setChoiceProjectAdapter(){
        binding.rvProjects.adapter = choiceProjectAdapter
        choiceProjectAdapter.onChoiceProjectClickListener = {
            findNavController().navigate(ChoiceProjectModalDirections.actionChoiceProjectModalToFinanceFragment(it))
        }
    }

}