package com.example.crocoandroidapp.presentation.choosing_users.fragment

import android.os.Bundle
import android.view.View
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseFragment

class ChoosingUsersFragment : BaseFragment() {

    override fun getLayout() = R.layout.fragment_choosing_users

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {

    }
}