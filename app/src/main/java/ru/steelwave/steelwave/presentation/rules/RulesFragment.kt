package ru.steelwave.steelwave.presentation.rules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.FragmentRulesBinding


class RulesFragment : Fragment() {

    private var _binding: FragmentRulesBinding? = null
    private val binding: FragmentRulesBinding
    get() = _binding ?: throw RuntimeException("FragmentRulesBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRulesBinding.inflate(inflater, container, false)
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