package com.trilema.doctrinalang.ui.week;

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

public class WeekFragment extends Fragment {

    private String[] days = {"lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche"};
    private String atual = "";
    private int anterior = -1;
    private Random rnd = new Random();

    private Button btnWeek01;
    private Button btnWeek02;
    private Button btnWeek03;
    private Button btnWeek04;
    private Button btnWeek05;
    private Button btnWeek06;
    private Button btnWeek07;

    private ImageView btnWeekRepeat;
    private ImageView btnWeekPlay;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_week, container, false);


        btnWeek01 = root.findViewById(R.id.btnWeek01);
        btnWeek02 = root.findViewById(R.id.btnWeek02);
        btnWeek03 = root.findViewById(R.id.btnWeek03);
        btnWeek04 = root.findViewById(R.id.btnWeek04);
        btnWeek05 = root.findViewById(R.id.btnWeek05);
        btnWeek06 = root.findViewById(R.id.btnWeek06);
        btnWeek07 = root.findViewById(R.id.btnWeek07);

        btnWeekPlay =  root.findViewById(R.id.btnWeekPlay);
        btnWeekRepeat = root.findViewById(R.id.btnWeekRepeat);

        btnWeek01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayPressed(view);
            }
        });
        btnWeek02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayPressed(view);
            }
        });
        btnWeek03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayPressed(view);
            }
        });
        btnWeek04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayPressed(view);
            }
        });
        btnWeek05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayPressed(view);
            }
        });
        btnWeek06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayPressed(view);
            }
        });
        btnWeek07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayPressed(view);
            }
        });
        btnWeekRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeBackgroundNumber(view);
                textToSpeech.speak(atual, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        btnWeekPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newRound();
            }
        });

        fadeBackgroundNumber(btnWeekPlay);

        return root;
    }


    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
        super.onDestroy();
    }

    private void dayPressed(View v) {
        int number = Integer.parseInt(v.getTag().toString());
        if (days[number - 1].equals(atual)) {
            correctAnswer(v);
            newRound();
        } else {
            tipAnswer(v);
        }
    }

    private void newRound() {
        int temp = 0;
        do {
            temp = rnd.nextInt(7);
        } while (temp == anterior);
        atual = days[temp];
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