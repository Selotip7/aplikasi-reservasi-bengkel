package com.my.projekakhir

import android.content.Context
import android.content.SharedPreferences

class UserPref(context: Context) {

    // Nama file SharedPreferences
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("UserPref", Context.MODE_PRIVATE)

    // Simpan nama user
    fun saveName(name: String) {
        sharedPref.edit().putString("username", name).apply()
    }

    // Ambil nama user, default "Nama Default" kalau belum disimpan
    fun getName(): String {
        return sharedPref.getString("username", "Nama Default") ?: "Nama Default"
    }

    // Simpan email user
    fun saveEmail(email: String) {
        sharedPref.edit().putString("email", email).apply()
    }

    // Ambil email user, default "email@default.com" kalau belum disimpan
    fun getEmail(): String {
        return sharedPref.getString("email", "email@default.com") ?: "email@default.com"
    }

    // Simpan nomor HP user
    fun saveNoHp(noHp: String) {
        sharedPref.edit().putString("noHp", noHp).apply()
    }

    // Ambil nomor HP user, default "08123456789" kalau belum disimpan
    fun getNoHp(): String {
        return sharedPref.getString("noHp", "08123456789") ?: "08123456789"
    }
}
