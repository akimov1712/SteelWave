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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ModalAddEmployeeBinding
import ru.steelwave.steelwave.presentation.main.employees.EmployeesState
import ru.steelwave.steelwave.presentation.main.employees.EmployeesViewModel
import ru.steelwave.steelwave.utils.compressImage
import javax.inject.Inject

@AndroidEntryPoint
class AddEmployeeModal: DialogFragment() {


    private val args by navArgs<AddEmployeeModalArgs>()

    private var _binding: ModalAddEmployeeBinding? = null
    private val binding: ModalAddEmployeeBinding
    get() = _binding ?: throw RuntimeException("ModalAddEmployeeBinding == null")

    private val viewModel by viewModels<EmployeesViewModel>()

    private val imagePickerLauncher =
        registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            it?.let { setImage(it) }
        }

    private var selectedImageBitmap: Bitmap? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ModalAddEmployeeBinding.inflate(inflater, container, false)
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
        setAdapterInDropMenu()
    }

    private fun observeViewModel(){
        with(viewModel){
            with(binding){
                state.observe(viewLifecycleOwner){
                    when(it){
                        is EmployeesState.ValidationPersonalDateSuccessfully -> {
                            clPersonalData.visibility = View.GONE
                            clPosition.visibility = View.VISIBLE
                        }
                        is EmployeesState.ValidationPositionSuccessfully -> {
                            clPosition.visibility = View.GONE
                            clRegistration.visibility = View.VISIBLE
                            btnContinue.visibility = View.GONE
                            btnAdd.visibility = View.VISIBLE
                        }
                        is EmployeesState.ErrorInputFirstName -> {
                            etFirstName.error = getString(R.string.field_not_can_empty)
                        }
                        is EmployeesState.ErrorInputLastName -> {
                            etLastName.error = getString(R.string.field_not_can_empty)
                        }
                        is EmployeesState.ErrorInputMiddleName -> {
                            etMiddleName.error = getString(R.string.field_not_can_empty)
                        }
                        is EmployeesState.ErrorInputPosition -> {
                            etPosition.error = getString(R.string.field_not_can_empty)
                        }
                        is EmployeesState.ErrorInputSalary -> {
                            etSalary.error = getString(R.string.salary_not_can_less_zero)
                        }
                        is EmployeesState.ErrorInputLogin -> {
                            etLogin.error = getString(R.string.min_lenght_login)
                        }
                        is EmployeesState.ErrorInputPassword -> {
                            etPassword.error = getString(R.string.min_lenght_password)
                        }
                        is EmployeesState.ErrorInputSecretWord -> {
                            etSecretWord.error = getString(R.string.field_not_can_empty)
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
            btnContinue.setOnClickListener {
                if (clPersonalData.isVisible){
                    val firstName = etFirstName.text.trim().toString()
                    val lastName = etLastName.text.trim().toString()
                    val middleName = etMiddleName.text.trim().toString()
                    viewModel.checkValidPersonalData(firstName,lastName,middleName, selectedImageBitmap)
                } else {
                    val position = etPosition.text.trim().toString()
                    val salary = etSalary.text.trim().toString()
                    viewModel.checkValidPosition(position, salary)
                }
            }
            btnAdd.setOnClickListener{
                val firstName = etFirstName.text.trim().toString()
                val lastName = etLastName.text.trim().toString()
                val middleName = etMiddleName.text.trim().toString()
                val position = etPosition.text.trim().toString()
                val salary = etSalary.text.trim().toString()
                val login = etLogin.text.trim().toString()
                val password = etPassword.text.trim().toString()
                val secretWord = etSecretWord.text.trim().toString()
                viewModel.addUser(firstName, lastName, middleName, selectedImageBitmap, position,
                    args.projectId, salary, login, password, secretWord)
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
            clPasteImage.setOnClickListener {
                pickImage()
            }
            clPasteImage.setOnLongClickListener {
                ivAvatar.setImageDrawable(resources.getDrawable(R.drawable.background_choice_image))
                selectedImageBitmap = null
                true
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
        val originalBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        selectedImageBitmap = compressImage(originalBitmap, 30)
        binding.ivAvatar.setImageBitmap(selectedImageBitmap)
    }

    private fun setAdapterInDropMenu(){
        val suggestions = requireContext().resources.getStringArray(R.array.proffesionArray)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_menu, suggestions)
        binding.etPosition.setAdapter(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}