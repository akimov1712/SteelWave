package ru.steelwave.steelwave.presentation.main.project.modals

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.databinding.ModalEditProjectBinding
import ru.steelwave.steelwave.presentation.CustomToast
import ru.steelwave.steelwave.presentation.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.project.ProjectViewModel
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


class EditProjectModal : BottomSheetDialogFragment() {

    private val args by navArgs<EditProjectModalArgs>()

    private val component by lazy{
        (requireActivity().application as App).component
    }

    private var _binding: ModalEditProjectBinding? = null
    private val binding: ModalEditProjectBinding
        get() = _binding ?: throw RuntimeException("ModalEditProjectBinding == null")

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

    private fun observeViewModel(){
        with(viewModel){
            getProjectItem(args.projectId)
            projectItem.observe(viewLifecycleOwner){ project ->
                binding.etNameProject.setText(project.name)
                selectedImageBitmap = project.previewImage
                binding.ivPreview.setImageBitmap(selectedImageBitmap)
                selectedDate = project.dateRelease
                setDate(selectedDate)
            }
            errorInputName.observe(viewLifecycleOwner){
                binding.etNameProject.error = "Введите название проекта"
            }
            errorImage.observe(viewLifecycleOwner){
                CustomToast.toastDefault(requireContext(), "Выберите обложку")
                binding.ivError.visibility = View.VISIBLE
            }
            shouldCloseScreen.observe(viewLifecycleOwner){
                dismiss()
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
            clPasteImage.setOnClickListener {
                imagePickerLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
            clChoiceDate.setOnClickListener {
                openDatePicker()
            }
            btnEdit.setOnClickListener{
                val name = binding.etNameProject.text.toString()
                viewModel.editProject(
                    inputName = name,
                    inputImage = selectedImageBitmap,
                    inputCreatedDate = selectedDate
                )
            }
            btnDelete.setOnClickListener {
                viewModel.deleteProjectItem()
                dismissNow()
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

    private fun setImage(uri: Uri) {
        selectedImageBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        binding.ivPreview.setImageBitmap(selectedImageBitmap)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val TAG_DIALOG_DATE_PICKER = "tag_dialog_date_picker"
    }

}