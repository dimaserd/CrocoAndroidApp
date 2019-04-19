package com.example.data.store

import android.content.SharedPreferences
import com.example.data.utils.modify

class PersistentStorage(private val preferences: SharedPreferences) {

    companion object {

        private const val COOKIES_KEY = "COOKIES_KEY"
    }

    fun putCookies(cookies: HashSet<String>) {
        // not safe
        preferences.modify { putStringSet(COOKIES_KEY, cookies) }
    }

    fun getCookies(): HashSet<String>? {
        return preferences.getStringSet(COOKIES_KEY, null) as HashSet<String>?
    }
}
