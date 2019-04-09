package com.example.crocoandroidapp.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun EditText.setOnTextChangedListener(function: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {

        override fun onTextChanged(newText: CharSequence?, p1: Int, p2: Int, p3: Int) {
            function(newText.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {}
    })
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeEnabled() {
    isEnabled = true
}

fun View.makeDisabled() {
    isEnabled = false
}

fun SwipeRefreshLayout.makeRefreshing() {
    isRefreshing = true
}

fun SwipeRefreshLayout.makeNotRefreshing() {
    isRefreshing = false
}
