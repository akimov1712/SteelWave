package ru.steelwave.steelwave.presentation.main.project.modals

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ModalEditProjectBinding
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.presentation.base.CustomToast
import ru.steelwave.steelwave.presentation.main.project.ProjectState
import ru.steelwave.steelwave.presentation.main.project.ProjectViewModel
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class EditProjectModal : BottomSheetDialogFragment() {

    private val args by navArgs<EditProjectModalArgs>()

    private var _binding: ModalEditProjectBinding? = null
    private val binding: ModalEditProjectBinding
        get() = _binding ?: throw RuntimeException("ModalEditProjectBinding == null")

    private val viewModel by viewModels<ProjectViewModel>()

    private val storagePermissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::onGotPermissionResultForStorage
    )

    private val imagePickerLauncher =
        registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            it?.let { setImage(it) }
        }

    private var choiceProject: ProjectModel? = null

    private var selectedImageBitmap: Bitmap? = null
    private var selectedDate = System.currentTimeMillis()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ModalEditProjectBinding.inflate(inflater, container, false)
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
    }


    private fun observeViewModel() {
        lifecycleScope.launch {
            with(viewModel) {
                getProjectItem(args.projectId)
                state.collect {
                    when (it) {
                        is ProjectState.ErrorImage -> {
                            CustomToast.toastDefault(requireContext(), "Выберите обложку")
                            binding.ivError.visibility = View.VISIBLE
                        }

                        is ProjectState.ErrorInputName -> {
                            binding.etNameProject.error = "Введите название проекта"
                        }

                        is ProjectState.ShouldCloseScreen -> {
                            findNavController().popBackStack()
                        }

                        is ProjectState.ProjectItem -> {
                            choiceProject = it.item
                            binding.etNameProject.setText(it.item.name)
                            selectedImageBitmap = it.item.previewImage
                            binding.ivPreview.setImageBitmap(selectedImageBitmap)
                            selectedDate = it.item.dateRelease
                            setDate(selectedDate)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun setDate(date: Long) {
        val calendar = Calendar.getInstance()
        calendar.time = Date(date)
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
            clPasteImage.setOnClickListener {
                storagePermissionRequestLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            clChoiceDate.setOnClickListener {
                openDatePicker()
            }
            btnEdit.setOnClickListener {
                val name = binding.etNameProject.text.toString()
                viewModel.editProject(
                    inputName = name,
                    inputImage = selectedImageBitmap,
                    inputCreatedDate = selectedDate
                )
            }
            btnDelete.setOnClickListener {
                choiceProject?.let {
                    findNavController().navigate(
                        EditProjectModalDirections.actionEditProjectModalToDeleteProjectModal(it)
                    )
                }
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

    private fun onGotPermissionResultForStorage(granted: Boolean) {
        if (granted) {
            pickImage()
        } else {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                askUserOpenAppSettingsPermission()
            } else {
                CustomToast.toastDefault(requireContext(), "Permission denied")
            }
        }

    }

    private fun askUserOpenAppSettingsPermission() {
        val appSettingIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", requireContext().packageName, null)
        )
        if (requireContext().packageManager.resolveActivity(
                appSettingIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            ) == null
        ) {
            CustomToast.toastDefault(requireContext(), "Permission are denied forever")
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("Разрешение отменено")
                .setMessage(getString(R.string.requestPermissionInAlertDialog))
                .setPositiveButton("Open") { _, _ ->
                    startActivity(appSettingIntent)
                }.create()
                .show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG_DIALOG_DATE_PICKER = "tag_dialog_date_picker"
    }

}