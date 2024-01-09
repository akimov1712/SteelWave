package ru.steelwave.steelwave.presentation.main.project.modals

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import ru.steelwave.steelwave.databinding.ModalAddProjectBinding
import ru.steelwave.steelwave.presentation.base.CustomToast
import ru.steelwave.steelwave.presentation.main.project.ProjectState
import ru.steelwave.steelwave.presentation.main.project.ProjectViewModel
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class AddProjectModal : DialogFragment() {


    private var _binding: ModalAddProjectBinding? = null
    private val binding: ModalAddProjectBinding
        get() = _binding ?: throw RuntimeException("ModalAddProjectBinding == null")

    private val viewModel by viewModels<ProjectViewModel>()

    private val imagePickerLauncher =
        registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            it?.let { setImage(it) }
        }


    private var selectedImageBitmap: Bitmap? = null
    private var selectedDate = System.currentTimeMillis()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ModalAddProjectBinding.inflate(inflater, container, false)
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
        setDate(selectedDate)
    }

    private fun observeViewModel() {
        with(viewModel) {
            state.observe(viewLifecycleOwner) {
                when (it) {
                    is ProjectState.ErrorImage -> {
                        CustomToast.toastDefault(requireContext(), "Выберите обложку")
                        binding.ivError.visibility = View.VISIBLE
                    }

                    is ProjectState.ErrorInputName -> {
                        binding.etNameProject.error = "Введите название проекта"
                    }

                    is ProjectState.ShouldCloseScreen -> {
                        dismiss()
                    }
                }
            }
        }
    }

    private fun setDate(date: Long) {
        val date = Date(date)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        setDateInView(day, month, year)
    }

    private fun setDateInView(day: Int, month: Int, year: Int) {
        with(binding) {
            tvDateDay.text = day.toString()
            tvDateMonth.text = month.toString()
            tvDateYear.text = year.toString()
        }
    }

    private fun setListenersInView() {
        with(binding) {
            btnContinue.setOnClickListener {
                val inputName = binding.etNameProject.text.toString()
                viewModel.addProject(inputName, selectedImageBitmap, selectedDate)
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
            clPasteImage.setOnClickListener {
                pickImage()
            }
            clChoiceDate.setOnClickListener {
                openDatePicker()
            }
        }
    }

    private fun openDatePicker() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setTitleText("Дата создание проекта")
            .build()
        picker.show(childFragmentManager, TAG_DIALOG_DATE_PICKER)

        picker.addOnPositiveButtonClickListener { selectedDate ->
            this.selectedDate = selectedDate
            setDate(this.selectedDate)
        }
    }

    private fun pickImage() {
        imagePickerLauncher.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    private fun setImage(uri: Uri) {
        selectedImageBitmap =
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        binding.ivPreview.setImageBitmap(selectedImageBitmap)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG_DIALOG_DATE_PICKER = "tag_dialog_date_picker"
    }

}