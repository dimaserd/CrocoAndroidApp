package com.example.data.utils

import android.content.SharedPreferences

inline fun SharedPreferences.modify(commit: Boolean = false, action: SharedPreferences.Editor.() -> Unit) {
    with(edit()) {
        action(this)
        if (commit) commit() else apply()
    }
}
