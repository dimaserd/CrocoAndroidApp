package com.example.crocoandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.crocoandroidapp.presentation.login.fragment.LoginFragment

class MainActivity : AppCompatActivity() {

//    private val router by inject<Router>()
//    private val navigatorHolder by inject<NavigatorHolder>()
//    private val navigator = SupportAppNavigator(this, -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_main_frame_layout_container, LoginFragment.newInstance())
            .commitAllowingStateLoss()
//        router.navigateTo(LoginScreen())
    }

    override fun onResume() {
        super.onResume()
//        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
//        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        router.exit()
    }
}
