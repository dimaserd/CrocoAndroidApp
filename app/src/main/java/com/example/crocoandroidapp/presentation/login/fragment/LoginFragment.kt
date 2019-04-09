package com.example.crocoandroidapp.presentation.login.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.crocoandroidapp.R
import com.example.crocoandroidapp.presentation.base.BaseFragment
import com.example.crocoandroidapp.presentation.base.LoadingScreenStates
import com.example.crocoandroidapp.presentation.base.LoadingStrategy
import com.example.crocoandroidapp.presentation.login.state.LoginViewState
import com.example.crocoandroidapp.presentation.login.viewmodel.LoginViewModel
import com.example.crocoandroidapp.presentation.login.state.LoginViewState.*
import com.example.crocoandroidapp.utils.observe
import com.example.crocoandroidapp.utils.setOnTextChangedListener
import com.jakewharton.rxbinding3.widget.textChanges
import com.redmadrobot.lib.sd.base.State
import com.redmadrobot.lib.sd.base.StateDelegate
import kotlinx.android.synthetic.main.fragment_login.fragment_login_text_input_layout_email as inputEmailLayout
import kotlinx.android.synthetic.main.fragment_login.fragment_login_material_button_login as buttonLogin
import kotlinx.android.synthetic.main.fragment_login.fragment_login_text_input_edit_text_email as inputEmail
import kotlinx.android.synthetic.main.fragment_login.fragment_login_text_input_edit_text_password as inputPassword
import kotlinx.android.synthetic.main.fragment_login.fragment_login_progress_bar_loading as progressBar
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment() {

    private val viewModel by inject<LoginViewModel>()
    private lateinit var screenState: StateDelegate<LoadingScreenStates>

    override fun getLayout() = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.stateCommand, ::onStateChanged)

        screenState = StateDelegate(
            State(
                LoadingScreenStates.LOADING,
                listOf(progressBar),
                LoadingStrategy(progressBar, listOf<View>(inputEmail, inputPassword, buttonLogin))
            ),
            State(LoadingScreenStates.CONTENT, emptyList())
        )

        initView()
    }

    private fun onStateChanged(state: LoginViewState) {
        when (state) {
            is Content -> screenState.currentState = LoadingScreenStates.CONTENT
            is Loading -> screenState.currentState = LoadingScreenStates.LOADING
            is Success -> navigateToMain()
            is RightEmail -> inputEmailLayout.error = null
            is WrongEmail -> inputEmailLayout.error = getString(R.string.wrong_email)
            is SnackBarErrorCommand -> {
                screenState.currentState = LoadingScreenStates.CONTENT
                showSnackbar(state.messageResource)
            }
        }
    }

    private fun initView() {
        buttonLogin.setOnClickListener {
            viewModel.onLoginButtonClicked()
        }

        inputEmail.textChanges()
            .skipInitialValue()
            .subscribe { viewModel.onEmailEntered(it.toString()) }
            .disposeOnDestroyView()

        inputPassword.setOnTextChangedListener(viewModel::onPasswordEntered)
    }

    private fun navigateToMain() {
        findNavController().navigate(R.id.croco_nav_graph_action_login_to_croco)
    }
}
