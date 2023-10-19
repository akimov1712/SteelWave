package ru.steelwave.steelwave.presentation.main.report

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.Const
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.FragmentReportBinding
import ru.steelwave.steelwave.presentation.base.ViewModelFactory
import ru.steelwave.steelwave.utils.formatName
import javax.inject.Inject


class ReportFragment : Fragment() {

    private val args by navArgs<ReportFragmentArgs>()

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private var _binding: FragmentReportBinding? = null
    private val binding: FragmentReportBinding
        get() = _binding ?: throw RuntimeException("FragmentReportBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy{
        ViewModelProvider(this,viewModelFactory)[ReportViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

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
    }

    private fun setViews(){
        refreshFragment()
    }

    private fun getData(){
        with(viewModel){
            getProject(args.projectId)
            getUser(args.userId)
            getTaskList(args.projectId, args.userId)
        }
    }

    private fun observeViewModel(){
        with(binding){
            with(viewModel){
                state.observe(viewLifecycleOwner){
                    when(it){
                        is ReportState.TaskList -> {

                        }
                        is ReportState.UserItem -> {
                            tvNameEmployee.text = formatName(it.userItem)
                            tvProfEmployee.text = it.userItem.position
                            if (it.userItem.avatar != null){
                                ivProfile.setImageBitmap(it.userItem.avatar)
                            } else {
                                ivProfile.visibility = View.INVISIBLE
                                ivDefaultAvatar.visibility = View.VISIBLE
                            }
                        }
                        is ReportState.ProjectItem -> {
                            tvProject.text = it.projectItem.name
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun setTaskAdapter(){

    }

    private fun setListenersInView(){

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