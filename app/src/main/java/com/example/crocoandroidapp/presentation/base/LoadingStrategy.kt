package com.example.crocoandroidapp.presentation.base

import android.view.View
import com.example.crocoandroidapp.utils.makeDisabled
import com.example.crocoandroidapp.utils.makeEnabled
import com.example.crocoandroidapp.utils.makeGone
import com.example.crocoandroidapp.utils.makeVisible
import com.redmadrobot.lib.sd.base.State
import com.redmadrobot.lib.sd.base.StateChangeStrategy

class LoadingStrategy<T : Enum<T>>(
    private val progressView: View,
    private val contentViews: List<View>
) : StateChangeStrategy<T> {

    override fun onStateEnter(state: State<T>, prevState: State<T>?) {
        super.onStateEnter(state, prevState)
        progressView.makeVisible()
        contentViews.forEach { it.makeDisabled() }
    }

    override fun onStateExit(state: State<T>, nextState: State<T>?) {
        progressView.makeGone()
        contentViews.forEach { it.makeEnabled() }
        super.onStateExit(state, nextState)
    }
}
