package ru.steelwave.steelwave.presentation.main.ads

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
import ru.steelwave.steelwave.databinding.FragmentAdsBinding
import ru.steelwave.steelwave.databinding.FragmentProjectBinding

class AdsFragment : Fragment() {

    private var _binding: FragmentAdsBinding? = null
    private val binding: FragmentAdsBinding
        get() = _binding ?: throw RuntimeException("FragmentAdsBinding == null")

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