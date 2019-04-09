package com.example.crocoandroidapp.presentation.base

import android.view.View
import com.redmadrobot.lib.sd.base.State
import com.redmadrobot.lib.sd.base.StateChangeStrategy

class LoadingStrategy<T : Enum<T>>(
    private val progressView: View,
    private val contentViews: List<View>
) : StateChangeStrategy<T> {

    override fun onStateEnter(state: State<T>, prevState: State<T>?) {
        super.onStateEnter(state, prevState)
        progressView.visibility = View.VISIBLE
        contentViews.forEach { it.isEnabled = false }
    }

    override fun onStateExit(state: State<T>, nextState: State<T>?) {
        progressView.visibility = View.GONE
        contentViews.forEach { it.isEnabled = true }
        super.onStateExit(state, nextState)
    }
}