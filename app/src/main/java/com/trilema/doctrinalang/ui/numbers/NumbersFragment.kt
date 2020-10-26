package com.trilema.doctrinalang.ui.numbers

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
import android.widget.*
import androidx.fragment.app.Fragment
import com.trilema.doctrinalang.R
import com.trilema.doctrinalang.support.SharedPref
import com.trilema.doctrinalang.support.Tagarela
import java.util.*

class NumbersFragment : Fragment() {
    private var textView: TextView? = null
    private var textTip: TextView? = null
    private var btnNumbersStart: ImageView? = null
    private var magicNumber = 0
    private val rnd = Random()
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
    private var imgInitial: ImageView? = null
    private var sw0100: Switch? = null
    private var sw1000: Switch? = null
    private var maxNumber = 0
    private var minNumber = 0
    private val fila: Queue<Int> = LinkedList()
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
        imgInitial = root.findViewById(R.id.imgInitial)
        sw0100 = root.findViewById(R.id.sw0100)
        sw1000 = root.findViewById(R.id.sw1000)
        maxNumber = 100
        btnZeroZero?.setEnabled(false)
        btnDoisPontos?.setEnabled(false)
        btnRepete?.setEnabled(false)
        btnVolta?.setEnabled(false)
        btnHelp?.setEnabled(false)
        val sp = SharedPref()
        if (sp.first(requireContext())) {
            imgInitial?.setVisibility(View.VISIBLE)
            imgInitial?.setOnClickListener(View.OnClickListener {
                sp.notFirst(requireContext())
                imgInitial!!.setVisibility(View.GONE)
            })
        }
        sw0100?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (!compoundButton.isPressed) {
                return@OnCheckedChangeListener
            }
            if (b) {
                minNumber = 0
                maxNumber = 100
                if (sw1000!!.isChecked()) maxNumber = 1000
            } else {
                minNumber = 100
                maxNumber = 1000
                if (!sw1000?.isChecked()!!) sw1000!!.setChecked(true)
            }
        })
        sw1000?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (!compoundButton.isPressed) {
                return@OnCheckedChangeListener
            }
            if (b) {
                minNumber = 100
                maxNumber = 1000
                if (sw0100?.isChecked()!!) minNumber = 0
            } else {
                minNumber = 0
                maxNumber = 100
                if (!sw0100!!.isChecked()) sw0100!!.setChecked(true)
            }
        })
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
        btnVolta?.setOnClickListener(View.OnClickListener { view ->
            fadeBackgroundNumber(view)
            textView?.setText("")
        })
        btnRepete?.setOnClickListener(View.OnClickListener { view ->
            fadeBackgroundNumber(view)
            Tagarela.sayIt(magicNumber.toString())
        })
        btnHelp?.setOnClickListener(View.OnClickListener { view ->
            fadeBackgroundNumber(view)
            textTip?.setText(magicNumber.toString())
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
        Tagarela.stop()
        super.onDestroy()
    }

    private fun numberPressed(v: View) {
        fadeBackgroundNumber(v)
        val number = v.tag.toString().toInt()
        val base = textView!!.text.toString()
        textView!!.text = base + number.toString()
        var numberDigitado: Int
        try {
            numberDigitado = textView!!.text.toString().toInt()
        } catch (e: Exception) {
            numberDigitado = -1
            textView!!.text = ""
        }
        if (numberDigitado == magicNumber) {
            textView!!.text = ""
            textTip!!.text = magicNumber.toString()
            correctAnswer()
            newRound()
        }
    }

    private fun newRound() {
        if (fila.size > 95) fila.poll()
        do {
            magicNumber = rnd.nextInt(maxNumber - minNumber + 1) + minNumber
        } while (fila.contains(magicNumber)) //to avoid repeated numbers
        fila.add(magicNumber)
        Tagarela.sayIt(magicNumber.toString())
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

    private fun fadeBackgroundNumber(v: View?) {
        val colorDrawables = arrayOf(ColorDrawable(resources.getColor(R.color.fundoForte)),
                ColorDrawable(resources.getColor(R.color.fundoPadrao)))
        val transitionDrawable = TransitionDrawable(colorDrawables)
        v!!.background = transitionDrawable
        transitionDrawable.startTransition(500)
    }

    private fun fadeTextTip() {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.BLACK, resources.getColor(R.color.fundoPadrao))
        colorAnimation.addUpdateListener { animator -> textTip!!.setTextColor((animator.animatedValue as Int)) }
        colorAnimation.duration = 2000
        colorAnimation.start()
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
        btnVolta!!.isEnabled = true
        btnRepete!!.isEnabled = true
        btnHelp!!.isEnabled = true
    }
}