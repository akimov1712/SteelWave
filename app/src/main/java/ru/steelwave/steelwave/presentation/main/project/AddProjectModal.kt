package ru.steelwave.steelwave.presentation.main.project

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.github.dhaval2404.imagepicker.ImagePicker
import ru.steelwave.steelwave.databinding.ModalAddProjectBinding


class AddProjectModal : DialogFragment() {

    private var _binding: ModalAddProjectBinding? = null
    private val binding: ModalAddProjectBinding
        get() = _binding ?: throw RuntimeException("ModalAddProjectBinding == null")

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { setImage(it) }
            } else {
                Toast.makeText(requireActivity(), "Ошибка", Toast.LENGTH_SHORT).show()
            }
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
    }

    private fun setupViews() {
        setListenersInView()
    }

    private fun setListenersInView() {
        with(binding) {
            btnContinue.setOnClickListener {
                Toast.makeText(requireContext(), "Запись добавлена", Toast.LENGTH_SHORT).show()
                dismiss()
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
            clPasteImage.setOnClickListener {
                ImagePicker.with(requireActivity())
                    .galleryOnly()
                    .galleryMimeTypes(arrayOf("image/*"))
                    .createIntent { imagePickerLauncher.launch(it) }

            }
        }
    }

    private fun setImage(uri: Uri) {
        val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        val drawable = BitmapDrawable(resources, bitmap)
        binding.clPasteImage.foreground = drawable
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}