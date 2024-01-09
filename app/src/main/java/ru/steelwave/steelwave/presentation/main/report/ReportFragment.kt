package ru.steelwave.steelwave.presentation.main.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.FragmentReportBinding
import ru.steelwave.steelwave.domain.entity.user.TaskModel
import ru.steelwave.steelwave.presentation.main.report.adapter.TaskAdapter
import ru.steelwave.steelwave.utils.formatName
import kotlin.random.Random

@AndroidEntryPoint
class ReportFragment : Fragment() {

    private val args by navArgs<ReportFragmentArgs>()

    private var _binding: FragmentReportBinding? = null
    private val binding: FragmentReportBinding
        get() = _binding ?: throw RuntimeException("FragmentReportBinding == null")

    private val viewModel by viewModels<ReportViewModel>()

    private val taskAdapter by lazy { TaskAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        observeViewModel()
        getData()
        val taskList = mutableListOf<TaskModel>()
        (0..100).forEach {
            taskList.add(
                TaskModel(
                    it, 1, 1,
                    "$it Я обращаюсь к вам с серьезной проблемой, которая произошла на вашей платформе, и прошу вашей помощи в разрешении этой ситуации. Недавно я взял заказ на вашей фриланс-площадке, и хотел бы поделиться своим опытом и недоразумениями, которые возникли в ходе выполнения этого заказа $it",
                    Random.nextInt(0, 2) == 0
                )
            )
        }
        taskAdapter.submitList(taskList)
    }

    private fun setViews() {
        refreshFragment()
        setTaskAdapter()
    }

    private fun getData() {
        with(viewModel) {
            getProject(args.projectId)
            getUser(args.userId)
//            getTaskList(args.projectId, args.userId)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            with(binding) {
                with(viewModel) {
                    state.collect {
                        when (it) {
                            is ReportState.TaskList -> {
                                taskAdapter.submitList(it.taskList)
                            }

                            is ReportState.UserItem -> {
                                tvNameEmployee.text = formatName(it.userItem)
                                tvProfEmployee.text = it.userItem.position
                                if (it.userItem.avatar != null) {
                                    ivProfile.setImageBitmap(it.userItem.avatar)
                                } else {
                                    ivProfile.visibility = View.INVISIBLE
                                    ivDefaultAvatar.visibility = View.VISIBLE
                                }
                            }

                            is ReportState.ProjectItem -> {
                                tvProject.text = it.projectItem.name
                            }

                            is ReportState.Loading -> {}
                        }
                    }
                }
            }
        }
    }

    private fun setTaskAdapter() {
        with(binding.rvTask) {
            recycledViewPool.setMaxRecycledViews(0, TaskAdapter.MAX_POOL_SIZE)
            adapter = taskAdapter
        }
    }

    private fun refreshFragment() {
        with(binding.swipeRefresh) {
            setColorSchemeResources(R.color.sw_purple)
            setOnRefreshListener {
                getData()
                CoroutineScope(Dispatchers.IO).launch {
                    delay(300)
                    isRefreshing = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}