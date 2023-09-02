package ru.steelwave.steelwave.presentation.main.project

import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.steelwave.steelwave.databinding.FragmentProjectBinding
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.entity.user.UserModel
import ru.steelwave.steelwave.presentation.main.project.projectAdapter.ProjectAdapter

class ProjectFragment : Fragment() {

    private var _binding: FragmentProjectBinding? = null
    private val binding: FragmentProjectBinding
    get() = _binding ?: throw RuntimeException("FragmentProjectBinding == null")

    private val projectAdapter by lazy {
        ProjectAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        initRecyclerView()
    }

    private fun setupViews(){
        setListenersInView()
    }

    private fun initRecyclerView(){
        setupProjectAdapter()
    }

    private fun setupProjectAdapter(){
        val project = ProjectModel(id = 1, name = "Кинг Мувис", dateRelease = 30654654645,)
        projectAdapter.submitList(listOf(project))
        binding.rvProjects.adapter = projectAdapter
    }

    private fun setListenersInView(){
        with(binding){
            btnAddProject.setOnClickListener {
                findNavController().navigate(ProjectFragmentDirections.actionProjectFragmentToAddProjectModal())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}