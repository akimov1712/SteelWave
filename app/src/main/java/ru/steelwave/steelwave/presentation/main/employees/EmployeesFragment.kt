package ru.steelwave.steelwave.presentation.main.employees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.Const
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.FragmentEmployeesBinding
import ru.steelwave.steelwave.domain.entity.user.UserModel
import ru.steelwave.steelwave.presentation.main.employees.adapters.userAdapter.UserAdapter

@AndroidEntryPoint
class EmployeesFragment : Fragment() {

    private val args by navArgs<EmployeesFragmentArgs>()
    private var projectId = Const.UNDEFINED_ID

    private var _binding: FragmentEmployeesBinding? = null
    private val binding: FragmentEmployeesBinding
        get() = _binding ?: throw RuntimeException("FragmentEmployeesBinding == null")

    private val viewModel by viewModels<EmployeesViewModel>()

    private val userAdapter by lazy { UserAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEmployeesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        setListenersInView()
        refreshFragment()
        setViewErrors()
        setValueFromArgs()
        setRecyclerViews()
    }

    private fun setRecyclerViews() {
        setUserAdapter()
    }

    private fun setUserAdapter() {
        binding.rvEmployees.adapter = userAdapter
        userAdapter.onEmployeesClickListener = { anchor, user ->
            setOpenMenuInEmployees(anchor, user)
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            with(binding) {
                if (projectId != Const.UNDEFINED_ID) {
                    getProjectItem(projectId)
                    getUserList(projectId, Const.START_LIMIT_USERS)
                    getCountUsers(projectId)
                }
                state.observe(viewLifecycleOwner) {
                    when (it) {
                        is EmployeesState.ProjectItem -> {
                            switchScreensAdding()
                            tvProjectEmployees.text = it.projectItem.name
                        }

                        is EmployeesState.UserList -> {
                            userAdapter.submitList(it.userList)
                            getCountUsers(projectId)
                            inclError.clError.visibility = View.GONE
                        }

                        is EmployeesState.CountUsers -> {
                            if (it.countUsers <= 3) {
                                btnShowMore.visibility = View.GONE
                            }
                            tvCountEmployees.text = it.countUsers.toString()
                        }

                        is EmployeesState.ErrorEmployeesList -> {
                            inclError.clError.visibility = View.VISIBLE
                            tvCountEmployees.text = "0"
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun refreshFragment() {
        with(binding.swipeRefresh) {
            setColorSchemeResources(R.color.sw_purple)
            setOnRefreshListener {
                viewModel.getUserList(projectId, Const.START_LIMIT_USERS)
                binding.btnShowMore.visibility = View.VISIBLE
                CoroutineScope(Dispatchers.IO).launch {
                    delay(300)
                    isRefreshing = false
                }
            }
        }
    }

    private fun switchScreensAdding() {
        with(binding) {
            if (projectId != Const.UNDEFINED_ID) {
                tvChoiceProject.visibility = View.GONE
                clEmployees.visibility = View.VISIBLE
            } else {
                tvChoiceProject.visibility = View.VISIBLE
                clEmployees.visibility = View.GONE
            }
        }
    }

    private fun setValueFromArgs() {
        projectId = args.projectId
    }

    private fun setViewErrors() {
        with(binding) {
            inclError.tvNotFound.text = "Сотрудников в проекте\nне обнаружено"
        }
    }

    private fun setListenersInView() {
        with(binding) {
            tvProjectEmployees.setOnClickListener {
                findNavController().navigate(
                    EmployeesFragmentDirections.actionEmployeesFragmentToChoiceProjectModal(
                        Const.MODE_CHOICE_PROJECT_EMPLOYEES
                    )
                )
            }
            btnAddEmployees.setOnClickListener {
                findNavController().navigate(
                    EmployeesFragmentDirections.actionEmployeesFragmentToAddEmployeeModal(args.projectId)
                )
            }
            btnShowMore.setOnClickListener {
                viewModel.getUserList(projectId, Const.NO_LIMIT)
                btnShowMore.visibility = View.GONE
            }
        }
    }

    private fun setOpenMenuInEmployees(anchorView: View, user: UserModel) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.menuInflater.inflate(R.menu.employees_menu, popup.menu)
        popup.setForceShowIcon(true)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_personal_data -> {
                    findNavController().navigate(
                        EmployeesFragmentDirections
                            .actionEmployeesFragmentToChangePersonalDataModal(user.id)
                    )
                    true
                }

                R.id.menu_position -> {
                    findNavController().navigate(
                        EmployeesFragmentDirections
                            .actionEmployeesFragmentToChangePositionModal(user.id)
                    )
                    true
                }

                R.id.menu_salary -> {
                    findNavController().navigate(
                        EmployeesFragmentDirections
                            .actionEmployeesFragmentToChangeSalaryModal(user.id)
                    )
                    true
                }

                R.id.menu_report -> {
                    findNavController().navigate(
                        EmployeesFragmentDirections
                            .actionEmployeesFragmentToReportFragment(projectId, user.id)
                    )
                    true
                }

                R.id.menu_kick -> {
                    findNavController().navigate(
                        EmployeesFragmentDirections
                            .actionEmployeesFragmentToKickEmployeeModal(user)
                    )
                    true
                }

                else -> {
                    false
                }
            }
        }
        popup.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}