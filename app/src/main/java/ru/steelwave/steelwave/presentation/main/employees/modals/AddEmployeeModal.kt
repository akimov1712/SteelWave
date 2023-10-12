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
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import ru.steelwave.steelwave.App
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ModalAddEmployeeBinding
import ru.steelwave.steelwave.presentation.CustomToast
import ru.steelwave.steelwave.presentation.ViewModelFactory
import ru.steelwave.steelwave.presentation.main.employees.EmployeesState
import ru.steelwave.steelwave.presentation.main.employees.EmployeesViewModel
import javax.inject.Inject

class AddEmployeeModal: DialogFragment() {

    private val component by lazy{
        (requireActivity().application as App).component
    }

    private var _binding: ModalAddEmployeeBinding? = null
    private val binding: ModalAddEmployeeBinding
    get() = _binding ?: throw RuntimeException("ModalAddEmployeeBinding == null")

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
                viewModel.projectList.observe(viewLifecycleOwner){
                    val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_menu, it)
                    binding.etProject.setAdapter(adapter)
                }
                viewModel.state.observe(viewLifecycleOwner){
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
                        is EmployeesState.ErrorInputProject -> {
                            etProject.error = getString(R.string.field_not_can_empty)
                        }
                        is EmployeesState.ErrorInputPosition -> {
                            etPosition.error = getString(R.string.field_not_can_empty)
                        }
                        is EmployeesState.ErrorInputSalary -> {
                            etSalary.error = getString(R.string.field_not_can_empty)
                        }
                        is EmployeesState.ErrorInputAvatar -> {
                            CustomToast.toastDefault(requireContext(), "Аватар не может быть пустым")
                            ivError.visibility = View.VISIBLE
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
                    val project = etProject.text.trim().toString()
                    val salary = etSalary.text.trim().toString()
                    viewModel.checkValidPosition(position, project, salary)
                }
            }
            btnAdd.setOnClickListener{
                val firstName = etFirstName.text.trim().toString()
                val lastName = etLastName.text.trim().toString()
                val middleName = etMiddleName.text.trim().toString()
                val position = etPosition.text.trim().toString()
                val project = etProject.text.trim().toString()
                val salary = etSalary.text.trim().toString()
                val login = etLogin.text.trim().toString()
                val password = etPassword.text.trim().toString()
                val secretWord = etSecretWord.text.trim().toString()
                viewModel.addUser(firstName, lastName, middleName, selectedImageBitmap, position,
                    project, salary, login, password, secretWord)
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
            clPasteImage.setOnClickListener {
                pickImage()
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