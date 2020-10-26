package com.trilema.doctrinalang.support

import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.TextView
import com.trilema.doctrinalang.R
import java.util.*

object Tagarela {
    const val LOGKEY = "DOCTRINALANG"
    lateinit var tts: TextToSpeech
    lateinit var context: Context
    lateinit var view: TextView
    var ACTUAL_LANGUAGE = 0
    fun sayIt(text: String) {
        when (ACTUAL_LANGUAGE) {
            0 -> tts?.setLanguage(Locale.FRENCH)
            1 -> tts?.setLanguage(Locale.GERMAN)
        }
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun sayMonth(number: Int) {
        var position = number - 1
        var months: Array<String> = arrayOf("")
        when (ACTUAL_LANGUAGE) {
            0 -> {
                tts?.setLanguage(Locale.FRENCH)
                months = context.resources.getStringArray(R.array.french_months)
            }
            1 -> {
                tts?.setLanguage(Locale.GERMAN)
                months = context.resources.getStringArray(R.array.german_months)
            }
        }
        tts?.speak(months.get(position), TextToSpeech.QUEUE_FLUSH, null, null)
    }


    fun sayWeek(number: Int) {
        var position = number - 1
        var week: Array<String> = arrayOf("")
        when (ACTUAL_LANGUAGE) {
            0 -> {
                tts?.setLanguage(Locale.FRENCH)
                week = context.resources.getStringArray(R.array.french_week)
            }
            1 -> {
                tts?.setLanguage(Locale.GERMAN)
                week = context.resources.getStringArray(R.array.german_week)
            }
        }
        tts?.speak(week.get(position), TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun sayTime(hour: Int, minute: Int) {
        var middle = ""
        var prefix = ""
        var posfix = ""
        when (ACTUAL_LANGUAGE) {
            0 -> {
                tts?.setLanguage(Locale.FRENCH)
                middle = if (hour > 1) " heures "
                else " heure "
            }
            1 -> {
                tts?.setLanguage(Locale.GERMAN)
                prefix = " Es ist "
                middle = " Uhr "
            }
        }
        tts?.speak("$prefix$hour$middle$minute$posfix", TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun sayPositionQuestion() {
        when (ACTUAL_LANGUAGE) {
            0 -> {
                tts?.setLanguage(Locale.FRENCH)
                tts?.speak(context.resources.getString(R.string.french_position_question), TextToSpeech.QUEUE_FLUSH, null, null)
            }
            1 -> {
                tts?.setLanguage(Locale.GERMAN)
                tts?.speak(context.resources.getString(R.string.german_position_question), TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    fun sayPosition(position:Int) {
        var frases: Array<String> = arrayOf("")
        when (ACTUAL_LANGUAGE) {
            0 -> {
                tts?.setLanguage(Locale.FRENCH)
                frases = context.resources.getStringArray(R.array.french_position)
            }
            1 -> {
                tts?.setLanguage(Locale.GERMAN)
                frases = context.resources.getStringArray(R.array.german_position)
            }
        }
        tts?.speak(frases.get(position), TextToSpeech.QUEUE_FLUSH, null, null)
    }


    fun stop() {
        if (tts != null) {
            tts?.stop()
        }
    }

    fun checkListener() {
        if (view!=null)
            view.setText("teste")
    }


}