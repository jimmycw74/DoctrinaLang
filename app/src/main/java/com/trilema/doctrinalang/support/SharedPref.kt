package com.trilema.doctrinalang.support

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.speech.tts.TextToSpeech

class SharedPref {
    val FILE = "doctrina_lang_pref"
    fun first(context: Context): Boolean {
        val retorno: Boolean
        val sharedPreferences = context.getSharedPreferences(FILE, Context.MODE_PRIVATE)
        retorno = sharedPreferences.getBoolean("first", true)
        return retorno
    }

    fun notFirst(context: Context) {
        val sharedPreferences = context.getSharedPreferences(FILE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("first", false)
        editor.apply()
    }

    fun getLanguage(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(FILE, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("language", 0)
    }

    fun setLanguage(context: Context,lang:Int) {
        val sharedPreferences = context.getSharedPreferences(FILE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("language", lang)
        editor.apply()
    }

}