package com.example.gympal2.auth

import android.content.Context

class TokenManager(private val context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString(AUTH_TOKEN, token).apply()
    }

    fun saveUserId(userId: String) {
        prefs.edit().putString(AUTH_USER_ID, userId).apply()
    }

    fun getToken(): String? {
        return prefs.getString(AUTH_TOKEN, null)
    }

    fun getUserId(): String? {
        return prefs.getString(AUTH_USER_ID, null)
    }

    fun clearToken() {
        prefs.edit().remove(AUTH_TOKEN).apply()
    }

    fun clearUserId() {
        prefs.edit().remove(AUTH_USER_ID).apply()
    }

    fun isTokenValid(token: String?): Boolean {
        return !(token == null || token.toString().isEmpty())
    }
}