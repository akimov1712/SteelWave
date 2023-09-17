package ru.steelwave.steelwave.presentation.main.employees

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.databinding.FragmentEmployeesBinding

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
        refreshFragment()
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