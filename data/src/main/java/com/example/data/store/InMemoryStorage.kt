package com.example.data.store

import java.util.concurrent.ConcurrentHashMap

class InMemoryStorage {

    companion object {

        private const val AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY"
    }

    private val hashMap = ConcurrentHashMap<String, Any>()

    fun putAuthToken(authToken: String) {
        hashMap[AUTH_TOKEN_KEY] = authToken
    }

    fun getAuthToken(): String? {
        return hashMap[AUTH_TOKEN_KEY] as? String
    }

    fun clearAllData() {
        hashMap.clear()
    }
}