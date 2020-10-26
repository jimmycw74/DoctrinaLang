package com.trilema.doctrinalang.ui.position

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.trilema.doctrinalang.R
import com.trilema.doctrinalang.support.Tagarela
import com.trilema.doctrinalang.support.Tagarela.tts
import java.util.*

class PositionFragment : Fragment() {
    private val images = intArrayOf(R.drawable.p01, R.drawable.p02, R.drawable.p03, R.drawable.p04, R.drawable.p05, R.drawable.p06, R.drawable.p07, R.drawable.p08, R.drawable.p09, R.drawable.p10, R.drawable.p10)
    private var actual = 0
    private var previous = -1
    private val rnd = Random()
    private var corOriginal:Int = 0
    var txtPosTitle: TextView? = null
    var imgPos01: ImageView? = null
    var imgPos02: ImageView? = null
    var imgPos03: ImageView? = null
    var imgPos04: ImageView? = null
    var btnPosStart: ImageView? = null
    var btnPosRepete: ImageView? = null
    var btnPosHelp: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_position, container, false)
        imgPos01 = root.findViewById(R.id.imgPos01)
        imgPos02 = root.findViewById(R.id.imgPos02)
        imgPos03 = root.findViewById(R.id.imgPos03)
        imgPos04 = root.findViewById(R.id.imgPos04)
        btnPosStart = root.findViewById(R.id.btnPosStart)
        btnPosHelp = root.findViewById(R.id.btnPosHelp)
        btnPosRepete = root.findViewById(R.id.btnPosRepete)
        txtPosTitle = root.findViewById(R.id.txtPosTitle)
        corOriginal = txtPosTitle!!.currentTextColor

        imgPos01?.setEnabled(false)
        imgPos02?.setEnabled(false)
        imgPos03?.setEnabled(false)
        imgPos04?.setEnabled(false)
        btnPosHelp?.setEnabled(false)
        btnPosRepete?.setEnabled(false)
        btnPosStart?.setOnClickListener(View.OnClickListener {
            previous = -1
            newRound()
        })
        btnPosRepete?.setOnClickListener(View.OnClickListener { Tagarela.sayPosition(actual) })
        imgPos01?.setOnClickListener(View.OnClickListener { view -> if (view.tag.toString().toInt() == actual) acerto() else erro() })
        imgPos02?.setOnClickListener(View.OnClickListener { view -> if (view.tag.toString().toInt() == actual) acerto() else erro() })
        imgPos03?.setOnClickListener(View.OnClickListener { view -> if (view.tag.toString().toInt() == actual) acerto() else erro() })
        imgPos04?.setOnClickListener(View.OnClickListener { view -> if (view.tag.toString().toInt() == actual) acerto() else erro() })
        return root
    }

    private fun newRound() {
        if (previous == -1) {
            imgPos01!!.isEnabled = true
            imgPos02!!.isEnabled = true
            imgPos03!!.isEnabled = true
            imgPos04!!.isEnabled = true
            btnPosHelp!!.isEnabled = true
            btnPosRepete!!.isEnabled = true
            Tagarela.sayPositionQuestion()
        }
        val proibidas: MutableList<Int> = ArrayList()
        val escolhas: MutableList<Int> = ArrayList()
        var temp: Int
        //escolhe um alvo
        do {
            temp = rnd.nextInt(11)
        } while (temp == previous)
        actual = temp
        previous = actual
        proibidas.add(actual)
        when (actual) {
            5, 7 -> {
                proibidas.add(9)
                proibidas.add(10)
            }
            9 -> {
                proibidas.add(5)
                proibidas.add(7)
                proibidas.add(10)
            }
            10 -> {
                proibidas.add(5)
                proibidas.add(7)
                proibidas.add(9)
            }
        }
        //escolhe as imagens
        while (escolhas.size < 4) {
            do {
                temp = rnd.nextInt(11)
            } while (proibidas.contains(temp))
            escolhas.add(temp)
            proibidas.add(temp)
        }
        //sobrescreve uma opção com a opção correta
        escolhas[rnd.nextInt(4)] = actual
        //setup das imanges
        imgPos01!!.setImageResource(images[escolhas[0]])
        imgPos01!!.tag = escolhas[0]
        imgPos02!!.setImageResource(images[escolhas[1]])
        imgPos02!!.tag = escolhas[1]
        imgPos03!!.setImageResource(images[escolhas[2]])
        imgPos03!!.tag = escolhas[2]
        imgPos04!!.setImageResource(images[escolhas[3]])
        imgPos04!!.tag = escolhas[3]
        //fala a frase
        Tagarela.sayPosition(actual)
    }

    // afdde9
    private fun erro() {
        val colorAnim = ObjectAnimator.ofInt(txtPosTitle, "textColor",
                Color.RED, Color.toArgb(Color.pack(corOriginal)))
        colorAnim.duration = 500
        colorAnim.setEvaluator(ArgbEvaluator())
        colorAnim.start()
    }

    private fun acerto() {
        val colorAnim = ObjectAnimator.ofInt(txtPosTitle, "textColor",
                Color.GREEN, Color.toArgb(Color.pack(corOriginal)))
        colorAnim.duration = 500
        colorAnim.setEvaluator(ArgbEvaluator())
        colorAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                newRound()
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        colorAnim.start()
    }
}