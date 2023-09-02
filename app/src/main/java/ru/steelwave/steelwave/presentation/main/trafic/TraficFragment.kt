package ru.steelwave.steelwave.presentation.main.trafic

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}