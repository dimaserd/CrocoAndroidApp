package com.example.crocoandroidapp.presentation.main.fragment

import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseFragment

class MainFragment : BaseFragment() {

    companion object {

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun getLayout() = R.layout.fragment_main
}