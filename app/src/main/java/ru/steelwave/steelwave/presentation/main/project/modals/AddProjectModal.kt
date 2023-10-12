package ru.steelwave.steelwave.presentation.main.project.modals

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ModalAddProjectBinding
import ru.steelwave.steelwave.presentation.CustomToast
import ru.steelwave.steelwave.presentation.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.project.ProjectState
import ru.steelwave.steelwave.presentation.main.project.ProjectViewModel
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


class AddProjectModal : DialogFragment() {

    private val component by lazy{
        (requireActivity().application as App).component
    }

    private var _binding: ModalAddProjectBinding? = null
    private val binding: ModalAddProjectBinding
        get() = _binding ?: throw RuntimeException("ModalAddProjectBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy{
        ViewModelProvider(this,viewModelFactory)[ProjectViewModel::class.java]
    }

    private val imagePickerLauncher =
        registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            it?.let { setImage(it) }
        }

    private val storagePermissionRequestLauncher = registerForActivityResult(
        RequestPermission(),
        ::onGotPermissionResultForStorage
    )

    private var selectedImageBitmap: Bitmap? = null
    private var selectedDate = System.currentTimeMillis()

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

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

    private fun observeViewModel(){
        with(viewModel){
            state.observe(viewLifecycleOwner){
                when(it){
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

    private fun setDate(date: Long){
        val date = Date(date)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        setDateInView(day, month, year)
    }

    private fun setDateInView(day: Int, month: Int, year: Int){
        with(binding){
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
                storagePermissionRequestLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            clChoiceDate.setOnClickListener {
                openDatePicker()
            }
        }
    }

    private fun openDatePicker(){
        val picker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setTitleText("Дата создание проекта")
            .build()
        picker.show(childFragmentManager, TAG_DIALOG_DATE_PICKER)

        picker.addOnPositiveButtonClickListener{ selectedDate ->
            this.selectedDate = selectedDate
            setDate(this.selectedDate)
        }
    }

    private fun pickImage(){
        imagePickerLauncher.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    private fun setImage(uri: Uri) {
        selectedImageBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        binding.ivPreview.setImageBitmap(selectedImageBitmap)
    }

    private fun onGotPermissionResultForStorage(granted: Boolean){
        if (granted){
            pickImage()
        } else {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                askUserOpenAppSettingsPermission()
            } else {
                CustomToast.toastDefault(requireContext(), "Permission denied")
            }
        }

    }

    private fun askUserOpenAppSettingsPermission(){
        val appSettingIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", requireContext().packageName, null)
        )
        if (requireContext().packageManager.resolveActivity(
                appSettingIntent,
                PackageManager.MATCH_DEFAULT_ONLY
        ) == null){
            CustomToast.toastDefault(requireContext(), "Permission are denied forever")
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("Разрешение отменено")
                .setMessage(getString(R.string.requestPermissionInAlertDialog))
                .setPositiveButton("Open"){ _, _ ->
                    startActivity(appSettingIntent)
                }.create()
                .show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val TAG_DIALOG_DATE_PICKER = "tag_dialog_date_picker"
    }

}