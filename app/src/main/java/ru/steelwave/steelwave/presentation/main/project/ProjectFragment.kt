package ru.steelwave.steelwave.presentation.main.project

import android.app.ProgressDialog.show
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.databinding.FragmentProjectBinding
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.entity.user.UserModel
import ru.steelwave.steelwave.presentation.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.project.projectAdapter.ProjectAdapter
import javax.inject.Inject

class ProjectFragment : Fragment() {

    private val component by lazy{
        (requireActivity().application as App).component
    }

    private var _binding: FragmentProjectBinding? = null
    private val binding: FragmentProjectBinding
    get() = _binding ?: throw RuntimeException("FragmentProjectBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy{
        ViewModelProvider(this, viewModelFactory)[ProjectViewModel::class.java]
    }

    private val projectAdapter by lazy {
        ProjectAdapter()
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
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
        setupViewModel()
        with(viewModel){

        }
    }

    private fun setupViewModel(){
        observeViewModel()

    }

    private fun observeViewModel(){
        with(viewModel){
            projectList.observe(viewLifecycleOwner){
                projectAdapter.submitList(it)
                binding.tvCountProjects.text = it.size.toString()
            }
        }
    }

    private fun setupViews(){
        setListenersInView()
    }

    private fun initRecyclerView(){
        setRecyclerViewListeners()
        setupProjectAdapter()
    }

    private fun setRecyclerViewListeners(){
        projectAdapter.onClickEditProjectListener = {
            findNavController().navigate(ProjectFragmentDirections.actionProjectFragmentToEditProjectModal(it.id))
        }
    }

    private fun setupProjectAdapter(){
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