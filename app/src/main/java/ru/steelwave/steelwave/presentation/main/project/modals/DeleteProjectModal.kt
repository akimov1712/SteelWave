package ru.steelwave.steelwave.presentation.main.project.modals

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ModalConfirmDeleteProjectBinding
import ru.steelwave.steelwave.presentation.main.project.ProjectState
import ru.steelwave.steelwave.presentation.main.project.ProjectViewModel

class DeleteProjectModal : DialogFragment() {


    private val args by navArgs<DeleteProjectModalArgs>()

    private var _binding: ModalConfirmDeleteProjectBinding? = null
    private val binding: ModalConfirmDeleteProjectBinding
        get() = _binding ?: throw RuntimeException("ModalConfirmDeleteProjectBinding == null")

    private val viewModel by viewModels<ProjectViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ModalConfirmDeleteProjectBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        setListenersInView()
        setTextConfirm()
    }

    private fun observeViewModel() {
        with(viewModel) {
            state.observe(viewLifecycleOwner) {
                when (it) {
                    is ProjectState.ShouldCloseScreen -> {
                        findNavController().navigate(DeleteProjectModalDirections.actionDeleteProjectModalToProjectFragment())
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setTextConfirm() {
        with(binding) {
            val text =
                "${resources.getString(R.string.you_really_need_delete_project)} ${args.projectModel.name}"
            val spannableString = SpannableString(text)
            val startIndex = text.indexOf(args.projectModel.name)
            spannableString.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.sw_black_transparent)),
                0,
                startIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.sw_purple)),
                startIndex,
                startIndex + args.projectModel.name.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvDeleteProject.text = spannableString

        }
    }

    private fun setListenersInView() {
        with(binding) {
            btnDelete.setOnClickListener {
                viewModel.deleteProjectItem(args.projectModel.id)
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}