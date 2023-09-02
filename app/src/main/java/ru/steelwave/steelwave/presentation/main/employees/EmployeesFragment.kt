package ru.steelwave.steelwave.presentation.main.employees

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.FragmentEmployeesBinding
import ru.steelwave.steelwave.presentation.main.project.AddProjectModal
import ru.steelwave.steelwave.presentation.main.project.ProjectFragment

class EmployeesFragment : Fragment() {

    private var _binding: FragmentEmployeesBinding? = null
    private val binding: FragmentEmployeesBinding
        get() = _binding ?: throw RuntimeException("FragmentEmployeesBinding == null")

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
    }

    private fun setupViews(){
        setListenersInView()
    }

    private fun setListenersInView(){
        with(binding){
            btnAddEmployees.setOnClickListener {
                AddEmployeeModal().also {
                    it.show(childFragmentManager, TAG_DIALOG_ADD_EMPLOYEE)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val TAG_DIALOG_ADD_EMPLOYEE = "tag_dialog_add_employee"
    }

}