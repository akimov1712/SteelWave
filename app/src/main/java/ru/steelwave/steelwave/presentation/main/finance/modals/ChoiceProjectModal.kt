package ru.steelwave.steelwave.presentation.main.finance.modals

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.databinding.ModalChoiceProjectBinding
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.presentation.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.finance.FinanceViewModel
import ru.steelwave.steelwave.presentation.main.finance.modals.choiceProjectAdapter.ChoiceProjectAdapter
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
                setTitle(it)
            }
        }
    }

    private fun setupViews(){
        setRecyclerView()
    }

    private fun setTitle(list: List<ProjectModel>){
        if (list.isEmpty()){
            binding.tvTitle.text = "Список пуст.\nДобавьте хотябы один проект"
        }
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