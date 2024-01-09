package ru.steelwave.steelwave.presentation.main.employees.modals

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
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ModalChangePersonalDataBinding
import ru.steelwave.steelwave.domain.entity.user.UserModel
import ru.steelwave.steelwave.presentation.main.employees.EmployeesState
import ru.steelwave.steelwave.presentation.main.employees.EmployeesViewModel
import ru.steelwave.steelwave.utils.compressImage

@AndroidEntryPoint
class ChangePersonalDataModal : DialogFragment() {

    private val args by navArgs<ChangePersonalDataModalArgs>()

    private var _binding: ModalChangePersonalDataBinding? = null
    private val binding: ModalChangePersonalDataBinding
        get() = _binding ?: throw RuntimeException("ModalChangePersonalDataBinding == null")


    private val viewModel by viewModels<EmployeesViewModel>()

    private val imagePickerLauncher =
        registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            it?.let { getImageFromMedia(it) }
        }

    private var selectedImageBitmap: Bitmap? = null


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

    private fun setupViews() {
        setListenersInView()
    }

    private fun setValueViewFromArgs(user: UserModel) {
        with(binding) {
            etFirstName.setText(user.firstName)
            etLastName.setText(user.lastName)
            etMiddleName.setText(user.middleName)

        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            with(binding) {
                getUser(args.userId)
                state.observe(viewLifecycleOwner) {
                    when (it) {
                        is EmployeesState.UserItem -> {
                            setValueViewFromArgs(it.userItem)
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

                        is EmployeesState.ShouldCloseModal -> {
                            dismiss()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun setListenersInView() {
        with(binding) {
            btnAdd.setOnClickListener {
                val firstName = etFirstName.text.trim().toString()
                val lastName = etLastName.text.trim().toString()
                val middleName = etMiddleName.text.trim().toString()
                viewModel.changePersonalData(
                    args.userId,
                    firstName,
                    lastName,
                    middleName,
                    selectedImageBitmap
                )
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

    private fun pickImage() {
        imagePickerLauncher.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    private fun getImageFromMedia(uri: Uri) {
        val originalBitmap =
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        selectedImageBitmap = compressImage(originalBitmap, 30)
        binding.ivAvatar.setImageBitmap(selectedImageBitmap)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}