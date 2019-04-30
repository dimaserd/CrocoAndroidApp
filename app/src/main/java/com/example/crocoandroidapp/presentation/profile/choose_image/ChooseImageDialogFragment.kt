package com.example.crocoandroidapp.presentation.profile.choose_image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crocoandroidapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_dialog_fragment_choose_image.bottom_sheet_dialog_fragment_choose_image_linear_layout_camera as camera
import kotlinx.android.synthetic.main.bottom_sheet_dialog_fragment_choose_image.bottom_sheet_dialog_fragment_choose_image_linear_layout_gallery as gallery

class ChooseImageDialogFragment(
    private val onCameraClicked: () -> Unit,
    private val onGalleryClicked: () -> Unit
) : BottomSheetDialogFragment() {

    companion object {

        const val TAG = "ChooseImageDialogFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_dialog_fragment_choose_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        camera.setOnClickListener { onCameraClicked.invoke() }
        gallery.setOnClickListener { onGalleryClicked.invoke() }
    }
}
