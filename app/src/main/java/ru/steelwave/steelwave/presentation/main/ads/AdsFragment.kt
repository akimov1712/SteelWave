package ru.steelwave.steelwave.presentation.main.ads

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.Const
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.FragmentAdsBinding
import ru.steelwave.steelwave.databinding.FragmentProjectBinding
import ru.steelwave.steelwave.presentation.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.employees.EmployeesFragmentArgs
import ru.steelwave.steelwave.presentation.main.employees.EmployeesFragmentDirections
import ru.steelwave.steelwave.presentation.main.employees.EmployeesViewModel
import javax.inject.Inject

class AdsFragment : Fragment() {

    private val args by navArgs<AdsFragmentArgs>()
    private var projectId = Const.UNDEFINED_ID

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private var _binding: FragmentAdsBinding? = null
    private val binding: FragmentAdsBinding
        get() = _binding ?: throw RuntimeException("FragmentAdsBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AdsViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAdsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews(){
        setListenersInView()
        refreshFragment()
        setViewErrors()
        setValueFromArgs()
        observeViewModel()
    }

    private fun observeViewModel(){
        with(viewModel){
            with(binding){
                if (projectId != Const.UNDEFINED_ID) {
                    getProjectItem(projectId)
                }
                projectItem.observe(viewLifecycleOwner) {
                    switchScreensAdding()
                    tvProjectPartner.text = it.name
                }
            }
        }
    }

    private fun refreshFragment(){
        with(binding.swipeRefresh){
            setColorSchemeResources(R.color.sw_purple)
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
                clAds.visibility = View.VISIBLE
            } else {
                tvChoiceProject.visibility = View.VISIBLE
                clAds.visibility = View.GONE
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
            clProjectPartner.setOnClickListener {
                openModalChoiceProject()
            }
            btnAddPartner.setOnClickListener {
                openModalAddPartner(projectId)
            }
        }
    }

    private fun openModalAddPartner(projectId: Int){
        findNavController().navigate(AdsFragmentDirections.actionAdsFragmentToAddPartnerModal(projectId))
    }

    private fun openModalChoiceProject(){
        findNavController().navigate(
            AdsFragmentDirections.actionAdsFragmentToChoiceProjectModal(
                Const.MODE_CHOICE_PROJECT_ADS
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}