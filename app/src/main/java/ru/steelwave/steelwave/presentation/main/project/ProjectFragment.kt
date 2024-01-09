package ru.steelwave.steelwave.presentation.main.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.FragmentProjectBinding
import ru.steelwave.steelwave.presentation.main.project.projectAdapter.ProjectAdapter

@AndroidEntryPoint
class ProjectFragment : Fragment() {

    private var _binding: FragmentProjectBinding? = null
    private val binding: FragmentProjectBinding
        get() = _binding ?: throw RuntimeException("FragmentProjectBinding == null")

    private val viewModel by viewModels<ProjectViewModel>()

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
        setupViewModel()
    }

    private fun setupViewModel() {
        observeViewModel()

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.projectList.collect {
                projectAdapter.submitList(it)
                binding.tvCountProjects.text = it.size.toString()
            }
        }
    }

    private fun setupViews() {
        setListenersInView()
        refreshFragment()
    }

    private fun initRecyclerView() {
        setRecyclerViewListeners()
        setupProjectAdapter()
    }

    private fun setRecyclerViewListeners() {
        projectAdapter.onClickEditProjectListener = {
            findNavController().navigate(
                ProjectFragmentDirections.actionProjectFragmentToEditProjectModal(
                    it.id
                )
            )
        }
    }

    private fun setupProjectAdapter() {
        binding.rvProjects.adapter = projectAdapter
    }

    private fun refreshFragment() {
        with(binding.swipeRefresh) {
            setColorSchemeResources(R.color.sw_purple)
            setOnRefreshListener {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(300)
                    isRefreshing = false
                }
            }
        }
    }

    private fun setListenersInView() {
        with(binding) {
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