package ru.steelwave.steelwave.presentation.main.employees

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.Const
import ru.steelwave.steelwave.Loger
import ru.steelwave.steelwave.databinding.FragmentEmployeesBinding
import ru.steelwave.steelwave.presentation.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.employees.adapters.userAdapter.UserAdapter
import ru.steelwave.steelwave.presentation.main.employees.modals.AddEmployeeModal
import ru.steelwave.steelwave.presentation.main.traffic.TrafficFragmentArgs
import ru.steelwave.steelwave.presentation.main.traffic.TrafficFragmentDirections
import ru.steelwave.steelwave.presentation.main.traffic.TrafficViewModel
import javax.inject.Inject

class EmployeesFragment : Fragment() {

    private val args by navArgs<EmployeesFragmentArgs>()
    private var projectId = Const.UNDEFINED_ID

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private var _binding: FragmentEmployeesBinding? = null
    private val binding: FragmentEmployeesBinding
        get() = _binding ?: throw RuntimeException("FragmentEmployeesBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[EmployeesViewModel::class.java]
    }

    private val userAdapter by lazy {UserAdapter()}

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

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

    private fun setupViews(){
        setListenersInView()
        refreshFragment()
        setViewErrors()
        setValueFromArgs()
        setRecyclerViews()
    }

    private fun setRecyclerViews(){
        setUserAdapter()
    }

    private fun setUserAdapter(){
        binding.rvEmployees.adapter = userAdapter
    }

    private fun observeViewModel(){
        with(viewModel){
            with(binding){
                if (projectId != Const.UNDEFINED_ID) {
                    getProjectItem(projectId)
                    getUserList(projectId)
                }
                state.observe(viewLifecycleOwner){
                    when(it){
                        is EmployeesState.ProjectItem -> {
                            switchScreensAdding()
                            tvProjectEmployees.text = it.projectItem.name
                        }
                        is EmployeesState.UserList -> {
                            Loger.log(it.userList.toString())
                            userAdapter.submitList(it.userList)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun refreshFragment(){
        with(binding.swipeRefresh){
            setColorSchemeResources(ru.steelwave.steelwave.R.color.sw_purple)
            setOnRefreshListener {
                CoroutineScope(Dispatchers.IO).launch{
                    delay(300)
                    isRefreshing = false
                }
            }
        }
    }

    private fun switchScreensAdding() {
        with(binding){
            if (projectId != Const.UNDEFINED_ID){
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

    private fun setViewErrors(){
        with(binding){
//            inclErrorVisition.tvNotFound.text = "Посетителей за данный\nмесяц не обнаружено"
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}