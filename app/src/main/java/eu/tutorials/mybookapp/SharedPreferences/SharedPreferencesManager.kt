package eu.tutorials.mybookapp.SharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import eu.tutorials.mybookapp.Model.data.BookWithAuthor

class SharedPreferencesManager(context: Context) {
    private val SP_NAMA = "spName"
    private val SP_EMAIL = "spEmail"
    private val SP_ISLOGIN = "spIsLogin"
    private val SP_TOKEN = "spToken"
    private val SP_WISHLIST = "spWishlist"
    private val sharedPreference: SharedPreferences = context.getSharedPreferences("LoggedIn", Context.MODE_PRIVATE)
    private val spEditor = sharedPreference.edit()
    private val gson = Gson()

    fun saveSpString(spKey: String, value: String) {
        spEditor.putString(spKey, value)
        spEditor.apply()
    }

    fun saveSpInt(spKey: String, value: Int) {
        spEditor.putInt(spKey, value)
        spEditor.apply()
    }

    fun saveSpEmail(spKey: String, value: String) {
        spEditor.putString(spKey, value)
        spEditor.apply()
    }
    fun saveSpWishlist(list: List<BookWithAuthor>) {
        val jsonString = gson.toJson(list)
        spEditor.putString(SP_WISHLIST, jsonString)
        spEditor.apply()
    }

    fun saveSpLoginStatus(value: Boolean) {
        spEditor.putBoolean(SP_ISLOGIN, value)
        spEditor.apply()
    }

    fun saveSpToken(spKey: String, value: String) {
        spEditor.putString(spKey, value)
        spEditor.apply()
    }

    fun saveSpName(spKey: String, value: String) {
        spEditor.putString(spKey, value)
        spEditor.apply()
    }

    fun getWishlist(): List<BookWithAuthor>? {
        val jsonString = sharedPreference.getString(SP_WISHLIST, null) ?: return null
        val typeToken = object : com.google.gson.reflect.TypeToken<List<BookWithAuthor>>() {}.type
        return gson.fromJson(jsonString, typeToken)
    }
    fun getName(): String? {
        return sharedPreference.getString(SP_NAMA, "")
    }

    fun getEmail(): String? {
        return sharedPreference.getString(SP_EMAIL, "")
    }

    fun getToken(): String? {
        return sharedPreference.getString(SP_TOKEN, "")
    }

    fun isLogin(): Boolean {
        return sharedPreference.getBoolean(SP_ISLOGIN, false)
    }
    fun clearSession() {
        spEditor.clear()
        spEditor.apply()
    }

}