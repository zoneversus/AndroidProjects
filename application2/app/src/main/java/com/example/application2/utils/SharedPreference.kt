package com.example.application2.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.application2.model.UserModel
import com.google.gson.Gson

class SharedPreference (val context: Context) {
    private val PREFS_NAME = "com_cognitus_kotlincodes"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor!!.commit()
    }
    fun save(KEY_NAME: String, value: UserModel) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        val json = Gson().toJson(value)
        editor.putString(KEY_NAME, json)
        editor!!.commit()
    }

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(KEY_NAME, value)
        editor.commit()
    }

    fun save(KEY_NAME: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, status!!)
        editor.commit()
    }

    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }
    fun getValueUserModel(KEY_NAME: String): UserModel?{
        val text=sharedPref.getString(KEY_NAME, null)
        val userModel = Gson().fromJson(text, UserModel::class.java)
        return userModel
    }
    fun getValueInt(KEY_NAME: String): Int {
        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun getValueBoolien(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue)
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.commit()
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.commit()
    }
}
