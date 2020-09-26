package com.trilema.doctrinalang.ui.numbers;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.trilema.doctrinalang.R;

import java.util.Random;

import static com.trilema.doctrinalang.support.Support.textToSpeech;

public class NumbersFragment extends Fragment {

    private TextView textView;
    private TextView textTip;
    private ImageView btnNumbersStart;
    private int magicNumber;
    private Random rnd = new Random();

    private Button btnZero;
    private Button btnUm;
    private Button btnDois;
    private Button btnTres;
    private Button btnQuatro;
    private Button btnCinco;
    private Button btnSeis;
    private Button btnSete;
    private Button btnOito;
    private Button btnNove;
    private Button btnZeroZero;
    private Button btnDoisPontos;
    private ImageView btnVolta;
    private ImageView btnRepete;
    private ImageView btnHelp;

    private Switch sw0100;
    private Switch sw1000;
    private int maxNumber = 0;
    private int minNumber = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        textView = root.findViewById(R.id.text_home);
        textTip = root.findViewById(R.id.txtTip);
        btnNumbersStart = root.findViewById(R.id.btnNumbersStart);

        btnZero = root.findViewById(R.id.btnZero);
        btnUm = root.findViewById(R.id.btnUm);
        btnDois = root.findViewById(R.id.btnDois);
        btnTres = root.findViewById(R.id.btnTres);
        btnQuatro = root.findViewById(R.id.btnQuatro);
        btnCinco = root.findViewById(R.id.btnCinco);
        btnSeis = root.findViewById(R.id.btnSeis);
        btnSete = root.findViewById(R.id.btnSete);
        btnOito = root.findViewById(R.id.btnOito);
        btnNove = root.findViewById(R.id.btnNove);
        btnVolta = root.findViewById(R.id.btnVolta);
        btnRepete = root.findViewById(R.id.btnRepete);
        btnHelp = root.findViewById(R.id.btnHelp);
        btnZeroZero = root.findViewById(R.id.btnZeroZero);
        btnDoisPontos = root.findViewById(R.id.btnDoisPontos);

        sw0100 = root.findViewById(R.id.sw0100);
        sw1000 = root.findViewById(R.id.sw1000);
        maxNumber = 100;

        btnZeroZero.setEnabled(false);
        btnDoisPontos.setEnabled(false);

        sw0100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isPressed()) {
                    return;
                }
                if (b) {
                    minNumber = 0;
                    maxNumber = 100;
                    if (sw1000.isChecked()) maxNumber = 1000;
                } else {
                    minNumber = 100;
                    maxNumber = 1000;
                    if (!sw1000.isChecked()) sw1000.setChecked(true);
                }
            }
        });
        sw1000.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isPressed()) {
                    return;
                }
                if (b) {
                    minNumber = 100;
                    maxNumber = 1000;
                    if (sw0100.isChecked()) minNumber = 0;
                } else {
                    minNumber = 0;
                    maxNumber = 100;
                    if (!sw0100.isChecked()) sw0100.setChecked(true);

                }
            }
        });

        btnZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        btnUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        btnDois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        btnTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        btnQuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        btnCinco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        btnSeis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        btnSete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        btnOito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        btnNove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        btnVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeBackgroundNumber(view);
                textView.setText("");
            }
        });
        btnRepete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeBackgroundNumber(view);
                textToSpeech.speak(String.valueOf(magicNumber), TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeBackgroundNumber(view);
                textTip.setText(String.valueOf(magicNumber));
                tipAnswer();
            }
        });


        btnNumbersStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeBackgroundNumber(view);
                newRound();
            }
        });

        fadeBackgroundNumber(btnNumbersStart);

        return root;
    }


    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
        super.onDestroy();
    }

    private void numberPressed(View v) {
        fadeBackgroundNumber(v);
        int number = Integer.parseInt(v.getTag().toString());
        String base = textView.getText().toString();
        textView.setText(base.concat(String.valueOf(number)));
        int numberDigitado;
        try {
            numberDigitado = Integer.parseInt(textView.getText().toString());
        } catch (Exception e) {
            numberDigitado = -1;
            textView.setText("");
        }
        if (numberDigitado == magicNumber) {
            textView.setText("");
            textTip.setText(String.valueOf(magicNumber));
            correctAnswer();
            newRound();
        }
    }

    private void newRound() {
        magicNumber = rnd.nextInt(maxNumber - minNumber + 1) + minNumber;
        textToSpeech.speak(String.valueOf(magicNumber), TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private void correctAnswer() {
        ColorDrawable[] colorDrawables = {new ColorDrawable(Color.GREEN),
                new ColorDrawable(getResources().getColor(R.color.fundoPadrao))};
        fadeBackground(colorDrawables);
        fadeTextTip();
    }

    private void tipAnswer() {
        ColorDrawable[] colorDrawables = {new ColorDrawable(Color.YELLOW),
                new ColorDrawable(getResources().getColor(R.color.fundoPadrao))};
        fadeBackground(colorDrawables);
        fadeTextTip();
    }

    private void fadeBackground(ColorDrawable[] colorDrawables) {
        TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawables);
        textTip.setBackground(transitionDrawable);
        transitionDrawable.startTransition(1000);
    }

    private void fadeBackgroundNumber(View v) {
        ColorDrawable[] colorDrawables = {new ColorDrawable(getResources().getColor(R.color.fundoForte)),
                new ColorDrawable(getResources().getColor(R.color.fundoPadrao))};
        TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawables);
        v.setBackground(transitionDrawable);
        transitionDrawable.startTransition(500);
    }

    private void fadeTextTip() {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), Color.BLACK, getResources().getColor(R.color.fundoPadrao));
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textTip.setTextColor((Integer) animator.getAnimatedValue());
            }
        });
        colorAnimation.setDuration(2000);
        colorAnimation.start();
    }

}