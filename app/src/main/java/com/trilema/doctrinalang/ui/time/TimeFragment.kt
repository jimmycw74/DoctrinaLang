package com.trilema.doctrinalang.ui.time

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.trilema.doctrinalang.R
import com.trilema.doctrinalang.support.Tagarela
import com.trilema.doctrinalang.support.Tagarela.tts
import java.util.*

class TimeFragment : Fragment() {
    private var textView: TextView? = null
    private var textTip: TextView? = null
    private var btnNumbersStart: ImageView? = null
    private var btnZero: Button? = null
    private var btnUm: Button? = null
    private var btnDois: Button? = null
    private var btnTres: Button? = null
    private var btnQuatro: Button? = null
    private var btnCinco: Button? = null
    private var btnSeis: Button? = null
    private var btnSete: Button? = null
    private var btnOito: Button? = null
    private var btnNove: Button? = null
    private var btnZeroZero: Button? = null
    private var btnDoisPontos: Button? = null
    private var btnVolta: ImageView? = null
    private var btnRepete: ImageView? = null
    private var btnHelp: ImageView? = null
    private var linMinMax: LinearLayout? = null
    private var magicHour = 0
    private var magicMinute = 0
    private val rnd = Random()
    private val filaHour: Queue<Int> = LinkedList()
    private val filaMinute: Queue<Int> = LinkedList()
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        textView = root.findViewById(R.id.text_home)
        textTip = root.findViewById(R.id.txtTip)
        btnNumbersStart = root.findViewById(R.id.btnNumbersStart)
        btnZero = root.findViewById(R.id.btnZero)
        btnUm = root.findViewById(R.id.btnUm)
        btnDois = root.findViewById(R.id.btnDois)
        btnTres = root.findViewById(R.id.btnTres)
        btnQuatro = root.findViewById(R.id.btnQuatro)
        btnCinco = root.findViewById(R.id.btnCinco)
        btnSeis = root.findViewById(R.id.btnSeis)
        btnSete = root.findViewById(R.id.btnSete)
        btnOito = root.findViewById(R.id.btnOito)
        btnNove = root.findViewById(R.id.btnNove)
        btnVolta = root.findViewById(R.id.btnVolta)
        btnRepete = root.findViewById(R.id.btnRepete)
        btnHelp = root.findViewById(R.id.btnHelp)
        btnZeroZero = root.findViewById(R.id.btnZeroZero)
        btnDoisPontos = root.findViewById(R.id.btnDoisPontos)
        linMinMax = root.findViewById(R.id.linMinMax)
        linMinMax?.setVisibility(View.GONE)
        btnRepete?.setEnabled(false)
        btnVolta?.setEnabled(false)
        btnHelp?.setEnabled(false)
        btnZero?.setOnClickListener(View.OnClickListener { view -> numberPressed(view) })
        btnUm?.setOnClickListener(View.OnClickListener { view -> numberPressed(view) })
        btnDois?.setOnClickListener(View.OnClickListener { view -> numberPressed(view) })
        btnTres?.setOnClickListener(View.OnClickListener { view -> numberPressed(view) })
        btnQuatro?.setOnClickListener(View.OnClickListener { view -> numberPressed(view) })
        btnCinco?.setOnClickListener(View.OnClickListener { view -> numberPressed(view) })
        btnSeis?.setOnClickListener(View.OnClickListener { view -> numberPressed(view) })
        btnSete?.setOnClickListener(View.OnClickListener { view -> numberPressed(view) })
        btnOito?.setOnClickListener(View.OnClickListener { view -> numberPressed(view) })
        btnNove?.setOnClickListener(View.OnClickListener { view -> numberPressed(view) })
        btnZeroZero?.setOnClickListener(View.OnClickListener { view -> numberPressed(view) })
        btnDoisPontos?.setOnClickListener(View.OnClickListener { view -> numberPressed(view) })
        btnVolta?.setOnClickListener(View.OnClickListener { view ->
            fadeBackgroundNumber(view)
            textView?.setText("")
        })
        btnRepete?.setOnClickListener(View.OnClickListener { view ->
            fadeBackgroundNumber(view)
            Tagarela.sayIt("$magicHour:$magicMinute")
        })
        btnHelp?.setOnClickListener(View.OnClickListener { view ->
            fadeBackgroundNumber(view)
            textTip?.setText(String.format("%02d:%02d", magicHour, magicMinute))
            tipAnswer()
        })
        btnNumbersStart?.setOnClickListener(View.OnClickListener { view ->
            fadeBackgroundNumber(view)
            ligaTudo()
            newRound()
        })
        fadeBackgroundNumber(btnNumbersStart)
        return root
    }

    override fun onDestroy() {
        if (tts != null) {
            tts?.stop()
        }
        super.onDestroy()
    }

    private fun numberPressed(v: View) {
        fadeBackgroundNumber(v)
        val number = v.tag.toString().toInt()
        if (number == -100) {
            val base = textView!!.text.toString()
            textView!!.text = base + "00"
        } else if (number == -101) {
            var hora = textView!!.text.toString()
            val horaInt: Int
            horaInt = try {
                hora.toInt()
            } catch (e: Exception) {
                0
            }
            hora = String.format(Locale.ENGLISH, "%02d", horaInt)
            textView!!.text = "$hora:"
        } else {
            val base = textView!!.text.toString()
            textView!!.text = base + number.toString()
        }
        val horaDigitada = textView!!.text.toString()
        val horaAtual = String.format(Locale.ENGLISH, "%02d:%02d", magicHour, magicMinute)
        if (horaDigitada == horaAtual) {
            textView!!.text = ""
            textTip!!.text = String.format(Locale.ENGLISH, "%02d:%02d", magicHour, magicMinute)
            correctAnswer()
            newRound()
        }
    }

    private fun newRound() {
        if (filaHour.size > 6) filaHour.poll()
        if (filaMinute.size > 30 ) filaMinute.poll()
        do {
            magicHour = rnd.nextInt(24)
        } while (filaHour.contains(magicHour)) //to avoid repeated hour
        do {
            magicMinute = rnd.nextInt(60)
        } while (filaMinute.contains(magicMinute)) // to avoid repeated minute
        filaHour.add(magicHour)
        filaMinute.add(magicMinute)
        Tagarela.sayTime(magicHour,magicMinute)
    }

    private fun correctAnswer() {
        val colorDrawables = arrayOf(ColorDrawable(Color.GREEN),
                ColorDrawable(resources.getColor(R.color.fundoPadrao)))
        fadeBackground(colorDrawables)
        fadeTextTip()
    }

    private fun tipAnswer() {
        val colorDrawables = arrayOf(ColorDrawable(Color.YELLOW),
                ColorDrawable(resources.getColor(R.color.fundoPadrao)))
        fadeBackground(colorDrawables)
        fadeTextTip()
    }

    private fun fadeBackground(colorDrawables: Array<ColorDrawable>) {
        val transitionDrawable = TransitionDrawable(colorDrawables)
        textTip!!.background = transitionDrawable
        transitionDrawable.startTransition(1000)
    }

    private fun fadeTextTip() {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.BLACK, resources.getColor(R.color.fundoPadrao))
        colorAnimation.addUpdateListener { animator -> textTip!!.setTextColor((animator.animatedValue as Int)) }
        colorAnimation.duration = 2000
        colorAnimation.start()
    }

    private fun fadeBackgroundNumber(v: View?) {
        val colorDrawables = arrayOf(ColorDrawable(resources.getColor(R.color.fundoForte)),
                ColorDrawable(resources.getColor(R.color.fundoPadrao)))
        val transitionDrawable = TransitionDrawable(colorDrawables)
        v!!.background = transitionDrawable
        transitionDrawable.startTransition(500)
    }

    private fun ligaTudo() {
        btnZero!!.isEnabled = true
        btnUm!!.isEnabled = true
        btnDois!!.isEnabled = true
        btnTres!!.isEnabled = true
        btnQuatro!!.isEnabled = true
        btnCinco!!.isEnabled = true
        btnSeis!!.isEnabled = true
        btnSete!!.isEnabled = true
        btnOito!!.isEnabled = true
        btnNove!!.isEnabled = true
        btnZeroZero!!.isEnabled = true
        btnDoisPontos!!.isEnabled = true
        btnVolta!!.isEnabled = true
        btnRepete!!.isEnabled = true
        btnHelp!!.isEnabled = true
    }
}