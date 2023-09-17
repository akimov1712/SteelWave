package ru.steelwave.steelwave.presentation.main.trafic

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.FragmentProjectBinding
import ru.steelwave.steelwave.databinding.FragmentTraficBinding

class TraficFragment : Fragment() {

    private var _binding: FragmentTraficBinding? = null
    private val binding: FragmentTraficBinding
        get() = _binding ?: throw RuntimeException("FragmentTraficBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTraficBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews(){
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}