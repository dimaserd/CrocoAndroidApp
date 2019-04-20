package com.example.crocoandroidapp.presentation.profile.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseFragment
import com.example.crocoandroidapp.presentation.base.LoadingViewState
import com.example.crocoandroidapp.presentation.base.LoadingViewState.*
import com.example.crocoandroidapp.presentation.edit_profile.fragment.EditProfileFragment.Companion.USER_EXTRA
import com.example.crocoandroidapp.presentation.profile.choose_image.dialog_fragment.ChooseImageDialogFragment
import com.example.crocoandroidapp.presentation.profile.viewmodel.ProfileViewModel
import com.example.crocoandroidapp.utils.makeGone
import com.example.crocoandroidapp.utils.makeVisible
import com.example.crocoandroidapp.utils.observe
import com.example.domain.model.Avatar
import com.example.domain.model.UserWithAvatar
import com.example.domain.utils.SexConverter
import org.koin.android.ext.android.inject
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
        private const val CAMERA_REQUEST = 2
        private const val GALLERY_REQUEST = 3
    }

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
                takePhoto()
            } else {
                showSnackbar(R.string.no_camera)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap
            imageViewAvatar.setImageBitmap(photo)
            viewModel.uploadAvatar(Avatar(photo))
        } else if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            // TODO or copy previous
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

    private fun takePhoto() {
        val cameraIntent = Intent(ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
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
            takePhoto()
        }
    }

    private fun onGalleryClicked() {
        chooseImageDialogFragment.dismiss()
        // TODO
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
