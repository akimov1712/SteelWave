package ru.steelwave.steelwave.presentation.main.employees.modals

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ModalChangePersonalDataBinding
import ru.steelwave.steelwave.presentation.base.CustomToast
import ru.steelwave.steelwave.presentation.base.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.employees.EmployeesState
import ru.steelwave.steelwave.presentation.main.employees.EmployeesViewModel
import javax.inject.Inject

class ChangePersonalDataModal: DialogFragment() {

    private val component by lazy{
        (requireActivity().application as App).component
    }
    private val args by navArgs<AddEmployeeModalArgs>()

    private var _binding: ModalChangePersonalDataBinding? = null
    private val binding: ModalChangePersonalDataBinding
    get() = _binding ?: throw RuntimeException("ModalChangePersonalDataBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this,viewModelFactory)[EmployeesViewModel::class.java]
    }

    private val imagePickerLauncher =
        registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            it?.let { setImage(it) }
        }

    private var selectedImageBitmap: Bitmap? = null

    override fun onAttach(activity: Activity) {
        component.inject(this)
        super.onAttach(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ModalChangePersonalDataBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun setupViews(){
        setListenersInView()
    }

    private fun observeViewModel(){
        with(viewModel){
            with(binding){
                state.observe(viewLifecycleOwner){
                    when(it){
                        is EmployeesState.ErrorInputFirstName -> {
                            etFirstName.error = getString(R.string.field_not_can_empty)
                        }
                        is EmployeesState.ErrorInputLastName -> {
                            etLastName.error = getString(R.string.field_not_can_empty)
                        }
                        is EmployeesState.ErrorInputMiddleName -> {
                            etMiddleName.error = getString(R.string.field_not_can_empty)
                        }
                        is EmployeesState.ShouldCloseModal -> {
                            dismiss()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun setListenersInView(){
        with(binding){

            btnAdd.setOnClickListener{
                val firstName = etFirstName.text.trim().toString()
                val lastName = etLastName.text.trim().toString()
                val middleName = etMiddleName.text.trim().toString()
//                viewModel.changePersonalData(firstName, lastName, middleName, selectedImageBitmap)
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
            clPasteImage.setOnClickListener {
                pickImage()
            }
            btnCancelImage.setOnClickListener {
                ivAvatar.setImageDrawable(resources.getDrawable(R.drawable.background_choice_image))
                selectedImageBitmap = null
                it.visibility = View.GONE
            }
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
        binding.ivAvatar.setImageBitmap(selectedImageBitmap)
        binding.btnCancelImage.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}