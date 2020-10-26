package com.trilema.doctrinalang.ui.week

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.trilema.doctrinalang.R
import com.trilema.doctrinalang.support.Tagarela
import com.trilema.doctrinalang.support.Tagarela.tts
import java.util.*

class WeekFragment : Fragment() {
    private var atual = 0
    private val rnd = Random()
    private var btnWeek01: Button? = null
    private var btnWeek02: Button? = null
    private var btnWeek03: Button? = null
    private var btnWeek04: Button? = null
    private var btnWeek05: Button? = null
    private var btnWeek06: Button? = null
    private var btnWeek07: Button? = null
    private var btnWeekRepeat: ImageView? = null
    private var btnWeekPlay: ImageView? = null
    private val filaWeek: Queue<Int> = LinkedList()


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_week, container, false)
        btnWeek01 = root.findViewById(R.id.btnWeek01)
        btnWeek02 = root.findViewById(R.id.btnWeek02)
        btnWeek03 = root.findViewById(R.id.btnWeek03)
        btnWeek04 = root.findViewById(R.id.btnWeek04)
        btnWeek05 = root.findViewById(R.id.btnWeek05)
        btnWeek06 = root.findViewById(R.id.btnWeek06)
        btnWeek07 = root.findViewById(R.id.btnWeek07)
        btnWeekPlay = root.findViewById(R.id.btnWeekPlay)
        btnWeekRepeat = root.findViewById(R.id.btnWeekRepeat)
        btnWeek01?.setOnClickListener(View.OnClickListener { view -> dayPressed(view) })
        btnWeek02?.setOnClickListener(View.OnClickListener { view -> dayPressed(view) })
        btnWeek03?.setOnClickListener(View.OnClickListener { view -> dayPressed(view) })
        btnWeek04?.setOnClickListener(View.OnClickListener { view -> dayPressed(view) })
        btnWeek05?.setOnClickListener(View.OnClickListener { view -> dayPressed(view) })
        btnWeek06?.setOnClickListener(View.OnClickListener { view -> dayPressed(view) })
        btnWeek07?.setOnClickListener(View.OnClickListener { view -> dayPressed(view) })
        btnWeekRepeat?.setOnClickListener(View.OnClickListener { view ->
            fadeBackgroundNumber(view)
            Tagarela.sayWeek(atual)
        })
        btnWeekPlay?.setOnClickListener(View.OnClickListener { newRound() })
        fadeBackgroundNumber(btnWeekPlay)
        return root
    }

    override fun onDestroy() {
        if (tts != null) {
            tts?.stop()
        }
        super.onDestroy()
    }

    private fun dayPressed(v: View) {
        val number = v.tag.toString().toInt()
        if (number == atual) {
            correctAnswer(v)
            newRound()
        } else {
            tipAnswer(v)
        }
    }

    private fun newRound() {
        if (filaWeek.size > 3) filaWeek.poll()
        do {
            atual = rnd.nextInt(7) + 1
        } while (filaWeek.contains(atual))
        filaWeek.add(atual);
        Tagarela.sayWeek(atual)
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