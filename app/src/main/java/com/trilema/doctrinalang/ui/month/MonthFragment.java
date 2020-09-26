package com.trilema.doctrinalang.ui.month;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.trilema.doctrinalang.R;

import java.util.Random;

import static com.trilema.doctrinalang.support.Support.textToSpeech;

public class MonthFragment extends Fragment {

    private String[] months = {"janvier" , "février" , "mars" , "avril" , "mai" , "juin" , "juillet" , "aout" , "septembre" , "octobre" , "novembre" , "décembre"};
    private String atual = "";
    private int anterior = -1;
    private Random rnd = new Random();

    private Button btnMonth01;
    private Button btnMonth02;
    private Button btnMonth03;
    private Button btnMonth04;
    private Button btnMonth05;
    private Button btnMonth06;
    private Button btnMonth07;
    private Button btnMonth08;
    private Button btnMonth09;
    private Button btnMonth10;
    private Button btnMonth11;
    private Button btnMonth12;

    private ImageView btnMonthPlay;
    private ImageView btnMonthRepeat;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_month, container, false);


        btnMonth01 = root.findViewById(R.id.btnMonth01);
        btnMonth02 = root.findViewById(R.id.btnMonth02);
        btnMonth03 = root.findViewById(R.id.btnMonth03);
        btnMonth04 = root.findViewById(R.id.btnMonth04);
        btnMonth05 = root.findViewById(R.id.btnMonth05);
        btnMonth06 = root.findViewById(R.id.btnMonth06);
        btnMonth07 = root.findViewById(R.id.btnMonth07);
        btnMonth08 = root.findViewById(R.id.btnMonth08);
        btnMonth09 = root.findViewById(R.id.btnMonth09);
        btnMonth10 = root.findViewById(R.id.btnMonth10);
        btnMonth11 = root.findViewById(R.id.btnMonth11);
        btnMonth12 = root.findViewById(R.id.btnMonth12);

        btnMonthPlay = root.findViewById(R.id.btnMonthPlay);
        btnMonthRepeat = root.findViewById(R.id.btnMonthRepeat);

        btnMonth01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mothPressed(view);
            }
        });
        btnMonth02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mothPressed(view);
            }
        });
        btnMonth03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mothPressed(view);
            }
        });
        btnMonth04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mothPressed(view);
            }
        });
        btnMonth05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mothPressed(view);
            }
        });
        btnMonth06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mothPressed(view);
            }
        });
        btnMonth07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mothPressed(view);
            }
        });
        btnMonth08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mothPressed(view);
            }
        });
        btnMonth09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mothPressed(view);
            }
        });
        btnMonth10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mothPressed(view);
            }
        });
        btnMonth11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mothPressed(view);
            }
        });
        btnMonth12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mothPressed(view);
            }
        });
        btnMonthRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeBackgroundNumber(view);
                textToSpeech.speak(atual, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        btnMonthPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newRound();
            }
        });

        fadeBackgroundNumber(btnMonthPlay);

        return root;
    }


    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
        super.onDestroy();
    }

    private void mothPressed(View v) {
        int number = Integer.parseInt(v.getTag().toString());
        if (months[number - 1].equals(atual)) {
            correctAnswer(v);
            newRound();
        } else {
            tipAnswer(v);
        }
    }

    private void newRound() {
        int temp = 0;
        do {
            temp = rnd.nextInt(12);
        } while (temp == anterior);
        atual = months[temp];
        anterior = temp;
        textToSpeech.speak(atual, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private void correctAnswer(View v) {
        ColorDrawable[] colorDrawables = {new ColorDrawable(Color.GREEN),
                new ColorDrawable(getResources().getColor(R.color.fundoPadrao))};
        fadeBackground(colorDrawables, v);
    }

    private void tipAnswer(View v) {
        ColorDrawable[] colorDrawables = {new ColorDrawable(Color.RED),
                new ColorDrawable(getResources().getColor(R.color.fundoPadrao))};
        fadeBackground(colorDrawables, v);
    }

    private void fadeBackground(ColorDrawable[] colorDrawables, View v) {
        TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawables);
        v.setBackground(transitionDrawable);
        transitionDrawable.startTransition(1000);
    }

    private void fadeBackgroundNumber(View v) {
        ColorDrawable[] colorDrawables = {new ColorDrawable(getResources().getColor(R.color.fundoForte)),
                new ColorDrawable(getResources().getColor(R.color.fundoPadrao))};
        TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawables);
        v.setBackground(transitionDrawable);
        transitionDrawable.startTransition(500);
    }

}