package io.usmon.registration.presentation.register

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import io.usmon.registration.R
import io.usmon.registration.databinding.FragmentRegisterBinding
import io.usmon.registration.presentation.base.BaseFragment
import io.usmon.registration.presentation.register.adapter.CountrySpinnerAdapter
import io.usmon.registration.presentation.register.util.RegisterChannel
import io.usmon.registration.presentation.register.util.RegisterEvent
import io.usmon.registration.util.extensions.collect
import io.usmon.registration.util.extensions.onChangedListener
import io.usmon.registration.util.extensions.sdk28orUp
import io.usmon.registration.util.extensions.update
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {


    override val viewModel: RegisterViewModel by viewModel()

    override fun myCreateView(savedInstanceState: Bundle?) {

        setupCountrySpinner()

        collect(viewModel.registerState) { state ->
            binding.fullName.update(state.fullName)
            binding.phoneNumber.update(state.phoneNumber)
            binding.county.setSelection(state.country + 1)
            binding.address.update(state.address)
            binding.password.update(state.password)
            binding.profileImage.setImageBitmap(state.image ?: return@collect)
        }

        collect(viewModel.registerEvent) { event ->
            when (event) {
                is RegisterChannel.ShowSnackbar -> {
                    Snackbar.make(
                        binding.root,
                        event.message.asString(requireContext()),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is RegisterChannel.Success -> {
                    // TODO: navigate to all users
                    findNavController().navigate(R.id.register_to_main)
                }
            }
        }


        binding.fullName.onChangedListener {
            viewModel.onEvent(RegisterEvent.FullNameChanged(it))
        }

        binding.phoneNumber.onChangedListener {
            viewModel.onEvent(RegisterEvent.PhoneNumberChanged(it))
        }

        binding.county.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.onEvent(RegisterEvent.CountryChanged(position))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }

        binding.address.onChangedListener {
            viewModel.onEvent(RegisterEvent.AddressChanged(it))
        }

        binding.password.onChangedListener {
            viewModel.onEvent(RegisterEvent.PasswordChanged(it))
        }

        binding.register.setOnClickListener {
            viewModel.onEvent(RegisterEvent.Register)
        }

        binding.profileImage.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setMessage(R.string.gallery_or_camera)
                setPositiveButton(R.string.camera) { _, _ ->
                    getImageFromCamera()
                }
                setNegativeButton(R.string.gallery) { _, _ ->
                    getImageFromGallery()
                }
                show()
            }
        }

    }

    private fun setupCountrySpinner() {
        binding.county.adapter = CountrySpinnerAdapter(
            resources.getStringArray(R.array.countries),
            resources.getString(R.string.choose_country)
        )
    }

    private fun getImageFromCamera() {
        cameraLauncher.launch()
    }

    private fun getImageFromGallery() {
        Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }.also {
            galleryLauncher.launch(it)
        }
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        result?.data?.data?.let { uri ->
            val bitmap = uriToBitmap(uri)
            viewModel.onEvent(RegisterEvent.ImageChanged(bitmap))
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        viewModel.onEvent(RegisterEvent.ImageChanged(it ?: return@registerForActivityResult))
    }

    @Suppress("DEPRECATION")
    private fun uriToBitmap(uri: Uri): Bitmap {
        val resolver = requireActivity().contentResolver
        return sdk28orUp {
            val source = ImageDecoder.createSource(resolver, uri)
            ImageDecoder.decodeBitmap(source)
        } ?: MediaStore.Images.Media.getBitmap(resolver, uri)
    }
}