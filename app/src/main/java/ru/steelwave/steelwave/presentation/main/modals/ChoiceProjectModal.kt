package ru.steelwave.steelwave.presentation.main.modals

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.Const
import ru.steelwave.steelwave.databinding.ModalChoiceProjectBinding
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.presentation.main.finance.FinanceViewModel
import ru.steelwave.steelwave.presentation.main.modals.choiceProjectAdapter.ChoiceProjectAdapter
import javax.inject.Inject

@AndroidEntryPoint
class ChoiceProjectModal : DialogFragment() {

    private val args by navArgs<ChoiceProjectModalArgs>()

    private var _binding: ModalChoiceProjectBinding? = null
    private val binding: ModalChoiceProjectBinding
        get() = _binding ?: throw RuntimeException("ModalChoiceProjectBinding == null")

    private val viewModel by viewModels<FinanceViewModel>()

    private val choiceProjectAdapter by lazy {
        ChoiceProjectAdapter()
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
        lifecycleScope.launch {
            with(viewModel){
                projectList.collect{
                    choiceProjectAdapter.submitList(it)
                    setTitle(it)
                }
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
            choiceModeProject(it)
        }
    }

    private fun choiceModeProject(projectId: Int) {
        when (args.screenMode) {
            Const.MODE_CHOICE_PROJECT_FINANCE -> {
                findNavController().navigate(
                    ChoiceProjectModalDirections.actionChoiceProjectModalToFinanceFragment(projectId)
                )
            }
            Const.MODE_CHOICE_PROJECT_TRAFFIC -> {
                findNavController().navigate(
                    ChoiceProjectModalDirections.actionChoiceProjectModalToTraficFragment(projectId)
                )
            }
            Const.MODE_CHOICE_PROJECT_EMPLOYEES -> {
                findNavController().navigate(
                    ChoiceProjectModalDirections.actionChoiceProjectModalToEmployeesFragment(projectId)
                )
            }
            Const.MODE_CHOICE_PROJECT_ADS -> {
                findNavController().navigate(
                    ChoiceProjectModalDirections.actionChoiceProjectModalToAdsFragment(projectId)
                )
            }
        }
    }

}