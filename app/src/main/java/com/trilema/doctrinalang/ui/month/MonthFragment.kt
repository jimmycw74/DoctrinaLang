package com.trilema.doctrinalang.ui.month

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
import androidx.fragment.app.Fragment
import com.trilema.doctrinalang.R
import com.trilema.doctrinalang.support.Tagarela
import java.util.*

class MonthFragment : Fragment() {
    private var atual = 0
    private val rnd = Random()
    private var btnMonth01: Button? = null
    private var btnMonth02: Button? = null
    private var btnMonth03: Button? = null
    private var btnMonth04: Button? = null
    private var btnMonth05: Button? = null
    private var btnMonth06: Button? = null
    private var btnMonth07: Button? = null
    private var btnMonth08: Button? = null
    private var btnMonth09: Button? = null
    private var btnMonth10: Button? = null
    private var btnMonth11: Button? = null
    private var btnMonth12: Button? = null
    private var btnMonthPlay: ImageView? = null
    private var btnMonthRepeat: ImageView? = null
    private val filaMonth: Queue<Int> = LinkedList()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_month, container, false)
        btnMonth01 = root.findViewById(R.id.btnMonth01)
        btnMonth02 = root.findViewById(R.id.btnMonth02)
        btnMonth03 = root.findViewById(R.id.btnMonth03)
        btnMonth04 = root.findViewById(R.id.btnMonth04)
        btnMonth05 = root.findViewById(R.id.btnMonth05)
        btnMonth06 = root.findViewById(R.id.btnMonth06)
        btnMonth07 = root.findViewById(R.id.btnMonth07)
        btnMonth08 = root.findViewById(R.id.btnMonth08)
        btnMonth09 = root.findViewById(R.id.btnMonth09)
        btnMonth10 = root.findViewById(R.id.btnMonth10)
        btnMonth11 = root.findViewById(R.id.btnMonth11)
        btnMonth12 = root.findViewById(R.id.btnMonth12)
        btnMonthPlay = root.findViewById(R.id.btnMonthPlay)
        btnMonthRepeat = root.findViewById(R.id.btnMonthRepeat)
        btnMonth01?.setOnClickListener(View.OnClickListener { view -> mothPressed(view) })
        btnMonth02?.setOnClickListener(View.OnClickListener { view -> mothPressed(view) })
        btnMonth03?.setOnClickListener(View.OnClickListener { view -> mothPressed(view) })
        btnMonth04?.setOnClickListener(View.OnClickListener { view -> mothPressed(view) })
        btnMonth05?.setOnClickListener(View.OnClickListener { view -> mothPressed(view) })
        btnMonth06?.setOnClickListener(View.OnClickListener { view -> mothPressed(view) })
        btnMonth07?.setOnClickListener(View.OnClickListener { view -> mothPressed(view) })
        btnMonth08?.setOnClickListener(View.OnClickListener { view -> mothPressed(view) })
        btnMonth09?.setOnClickListener(View.OnClickListener { view -> mothPressed(view) })
        btnMonth10?.setOnClickListener(View.OnClickListener { view -> mothPressed(view) })
        btnMonth11?.setOnClickListener(View.OnClickListener { view -> mothPressed(view) })
        btnMonth12?.setOnClickListener(View.OnClickListener { view -> mothPressed(view) })
        btnMonthRepeat?.setOnClickListener(View.OnClickListener { view ->
            fadeBackgroundNumber(view)
            Tagarela.sayMonth(atual)
        })
        btnMonthPlay?.setOnClickListener(View.OnClickListener { newRound() })
        fadeBackgroundNumber(btnMonthPlay)
        return root
    }

    override fun onDestroy() {
        if (Tagarela.tts != null) {
            Tagarela.tts!!.stop()
        }
        super.onDestroy()
    }

    private fun mothPressed(v: View) {
        val number = v.tag.toString().toInt()
        if (number == atual) {
            correctAnswer(v)
            newRound()
        } else {
            tipAnswer(v)
        }
    }

    private fun newRound() {
        if(filaMonth.size>6) filaMonth.poll()
        do {
            atual = rnd.nextInt(12) + 1
        } while (filaMonth.contains(atual))
        filaMonth.add(atual)
        Tagarela.sayMonth(atual)
    }

    private fun correctAnswer(v: View) {
        val colorDrawables = arrayOf(ColorDrawable(Color.GREEN),
                ColorDrawable(resources.getColor(R.color.fundoPadrao)))
        fadeBackground(colorDrawables, v)
    }

    private fun tipAnswer(v: View) {
        val colorDrawables = arrayOf(ColorDrawable(Color.RED),
                ColorDrawable(resources.getColor(R.color.fundoPadrao)))
        fadeBackground(colorDrawables, v)
    }

    private fun fadeBackground(colorDrawables: Array<ColorDrawable>, v: View) {
        val transitionDrawable = TransitionDrawable(colorDrawables)
        v.background = transitionDrawable
        transitionDrawable.startTransition(1000)
    }

    private fun fadeBackgroundNumber(v: View?) {
        val colorDrawables = arrayOf(ColorDrawable(resources.getColor(R.color.fundoForte)),
                ColorDrawable(resources.getColor(R.color.fundoPadrao)))
        val transitionDrawable = TransitionDrawable(colorDrawables)
        v!!.background = transitionDrawable
        transitionDrawable.startTransition(500)
    }
}