package com.example.crocoandroidapp.presentation.croco.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_croco.fragment_croco_bottom_navigation_view_navigation as bottomNavigation

class CrocoFragment : BaseFragment() {

    override fun getLayout() = R.layout.fragment_croco

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupWithNavController(
            bottomNavigation,
            Navigation.findNavController(activity!!, R.id.fragment_croco_fragment_navigation_host)
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        NavigationUI.onNavDestinationSelected(item, findNavController())
        return super.onOptionsItemSelected(item)
    }
}
