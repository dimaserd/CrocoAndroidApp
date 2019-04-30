package com.example.crocoandroidapp.presentation.profile.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.provider.MediaStore.EXTRA_OUTPUT
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseFragment
import com.example.crocoandroidapp.presentation.base.LoadingViewState
import com.example.crocoandroidapp.presentation.base.LoadingViewState.*
import com.example.crocoandroidapp.presentation.edit_profile.fragment.EditProfileFragment.Companion.USER_EXTRA
import com.example.crocoandroidapp.presentation.profile.choose_image.ChooseImageDialogFragment
import com.example.crocoandroidapp.presentation.profile.viewmodel.ProfileViewModel
import com.example.crocoandroidapp.utils.makeGone
import com.example.crocoandroidapp.utils.makeVisible
import com.example.crocoandroidapp.utils.observe
import com.example.domain.model.UserWithAvatar
import com.example.domain.utils.SexConverter
import org.koin.android.ext.android.inject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_constraint_layout_content as content
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_floating_action_bar_edit as editFab
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_image_view_avatar as imageViewAvatar
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_progress_bar_loading as progressBar
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_text_view_email as textViewEmail
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_text_view_name as textViewName
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_text_view_phone_number as textViewPhoneNumber
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_text_view_second_name as textViewSecondName
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_text_view_sex as textViewSex
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_text_view_third_name as textViewThirdName
import kotlinx.android.synthetic.main.fragment_profile.fragment_profile_text_view_zero_screen as zeroScreen

class ProfileFragment : BaseFragment() {

    companion object {

        private const val CAMERA_PERMISSION_CODE = 1
        private const val TAKE_PHOTO_REQUEST = 2
        private const val GALLERY_PERMISSION_CODE = 3
        private const val PICK_PHOTO_REQUEST = 4
    }

    private lateinit var currentPhotoPath: String

    private val viewModel by inject<ProfileViewModel>()
    private val chooseImageDialogFragment by lazy { ChooseImageDialogFragment(::onCameraClicked, ::onGalleryClicked) }

    override fun getLayout() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.stateCommand, ::onStateChanged)
        observe(viewModel.profileLiveData, ::onProfileChanged)

        initViews()
        showLoading()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoFromCamera()
            } else {
                showSnackbar(R.string.no_camera_access)
            }
        }

        if (requestCode == GALLERY_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoFromGallery()
            } else {
                showSnackbar(R.string.no_files_access)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val avatarUri = when (requestCode) {
                TAKE_PHOTO_REQUEST -> Uri.parse(currentPhotoPath)
                PICK_PHOTO_REQUEST -> data?.data
                else -> throw IllegalArgumentException("unknown source of image")
            }

            avatarUri?.let {
                imageViewAvatar.setImageURI(it)
                viewModel.uploadAvatar(it)
            }
        }
    }

    private fun initViews() {
        editFab.setOnClickListener {
            val bundle = bundleOf(USER_EXTRA to viewModel.profileLiveData.value?.user)
            findNavController().navigate(R.id.action_profile_to_edit_profile, bundle)
        }

        imageViewAvatar.setOnClickListener {
            chooseImageDialogFragment.show(activity?.supportFragmentManager, ChooseImageDialogFragment.TAG)
        }
    }

    private fun onStateChanged(state: LoadingViewState) {
        when (state) {
            is Content -> showContent()
            is Loading -> showLoading()
            is NoData -> showZeroScreen()
            is SnackBarCommand -> {
                showZeroScreen()
                showSnackbar(state.messageResource)
            }
        }
    }

    private fun takePhotoFromCamera() {
        Intent(ACTION_IMAGE_CAPTURE).also { takePhotoIntent ->
            takePhotoIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    val photoURI = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.crocoandroidapp.fileprovider",
                        it
                    )
                    takePhotoIntent.putExtra(EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST)
                }
            }
        }
    }

    private fun takePhotoFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(Intent.createChooser(galleryIntent, getString(R.string.pick_image)), PICK_PHOTO_REQUEST)
    }

    private fun onProfileChanged(userWithAvatar: UserWithAvatar) {
        showContent()

        with(userWithAvatar.user) {
            textViewName.text = firstName
            textViewSecondName.text = secondName ?: getString(R.string.not_mentioned)
            textViewThirdName.text = thirdName ?: getString(R.string.not_mentioned)
            textViewSex.text = getString(SexConverter.convertToResId(sex))
            textViewEmail.text = email
            textViewPhoneNumber.text = phoneNumber
        }

        Glide
            .with(requireContext())
            .load(userWithAvatar.avatar.bitmap)
            .centerCrop()
            .placeholder(R.drawable.logo)
            .into(imageViewAvatar)
    }

    private fun onCameraClicked() {
        chooseImageDialogFragment.dismiss()

        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        } else {
            takePhotoFromCamera()
        }
    }

    private fun onGalleryClicked() {
        chooseImageDialogFragment.dismiss()

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), GALLERY_PERMISSION_CODE)
        } else {
            takePhotoFromGallery()
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun showContent() {
        content.makeVisible()
        editFab.show()
        progressBar.makeGone()
        zeroScreen.makeGone()
    }

    private fun showLoading() {
        content.makeGone()
        editFab.hide()
        progressBar.makeVisible()
        zeroScreen.makeGone()
    }

    private fun showZeroScreen() {
        content.makeGone()
        editFab.hide()
        progressBar.makeGone()
        zeroScreen.makeVisible()
    }
}
