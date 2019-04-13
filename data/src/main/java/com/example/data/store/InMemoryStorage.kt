package com.example.data.store

import java.util.concurrent.ConcurrentHashMap

class InMemoryStorage {

    companion object {

        private const val COOKIES_KEY = "COOKIES_KEY"
    }

    private val hashMap = ConcurrentHashMap<String, Any>()

    fun putCookies(cookies: HashSet<String>) {
        hashMap[COOKIES_KEY] = cookies
    }

    @Suppress("UNCHECKED_CAST")
    fun getCookies(): HashSet<String>? {
        return hashMap[COOKIES_KEY] as? HashSet<String>
    }

    fun clearAllData() {
        hashMap.clear()
    }
}
