package com.example.crocoandroidapp.presentation.choose_users.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_choose_users.fragment_choose_user_material_button_choose_users as buttonChooseUsers

class ChooseUsersFragment : BaseFragment() {

    override fun getLayout() = R.layout.fragment_choose_users

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        buttonChooseUsers.setOnClickListener {
            findNavController().navigate(R.id.action_choose_users_to_choosing_users)
        }
    }
}
