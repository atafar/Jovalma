/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.riseapp.Fragments.InformatFragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.riseapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment3_Informat extends Fragment {
    public static final String CORRECT_ANSWER = "correct_answer";
    public static final String CURRENT_QUESTION = "current_question";
    public static final String ANSWER_IS_CORRECT = "answer_is_correct";
    public static final String ANSWER = "answer";

    private int ids_answers[] = {
            R.id.answer1, R.id.answer2, R.id.answer3
    };
    private String[] all_questions;

    private TextView text_question;
    private RadioGroup group;
    private Button btn_next, btn_prev;

    private int correct_answer;
    private int current_question;
    private boolean[] answer_is_a;
    private boolean[] answer_is_b;
    private boolean[] answer_is_c;
    private int[] answer;


    public TabFragment3_Informat() {
        // Required empty public constructor
    }


    View activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = inflater.inflate(R.layout.tab_fragment3_informat, container, false);
        text_question = (TextView) activity.findViewById(R.id.text_question);
        group = (RadioGroup) activity.findViewById(R.id.answer_group);
        btn_next = (Button) activity.findViewById(R.id.btn_check);
        btn_prev = (Button) activity.findViewById(R.id.btn_prev);

        all_questions = getResources().getStringArray(R.array.all_questions);


        startOver();



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                if (current_question < all_questions.length - 1) {
                    current_question++;
                    showQuestion();
                } else {
                    checkResults();
                }
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                if (current_question > 0) {
                    current_question--;
                    showQuestion();
                }
            }
        });
        return activity;
    }

    private void startOver() {
        answer_is_a = new boolean[all_questions.length];
        answer_is_b = new boolean[all_questions.length];
        answer_is_c = new boolean[all_questions.length];
        answer = new int[all_questions.length];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = -1;
        }
        current_question = 0;
        showQuestion();
    }

    private void checkResults() {
        int a = 0, b = 0, c = 0, nocontestadas = 0;
        for (int i = 0; i < all_questions.length; i++) {
            if (answer_is_a[i]) a++;
            else if (answer_is_b[i]) b++;
            else if (answer_is_c[i]) c++;
            else if (answer[i] == -1) nocontestadas++;

        }
        String message;
        String resultat;
        if (a > b) {
            if (a > c) {
                message = getString(R.string.Perill);
                resultat = getString(R.string.perill_titol);
            } else {
                message = getString(R.string.Estable);
                resultat = getString(R.string.estable_titol);
            }
        } else if (b > c) {
            message = getString(R.string.Alerta);
            resultat = getString(R.string.alerta_titol);
        } else {
            message = getString(R.string.Estable);
            resultat = getString(R.string.estable_titol);
        }
        // TODO: Permitir traducción de este texto:


        AlertDialog.Builder builder = new AlertDialog.Builder(activity.getContext());
        builder.setTitle(resultat);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.finish, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startOver();
            }
        });
        /*builder.setNegativeButton(R.string.start_over, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startOver();
            }
        });*/
        builder.create().show();
    }

    private void checkAnswer() {
        int id = group.getCheckedRadioButtonId();
        int ans = -1;
        for (int i = 0; i < ids_answers.length; i++) {
            if (ids_answers[i] == id) {
                ans = i;
            }
        }
        if (ans == 0) {
            answer_is_a[current_question] = true;
        } else if (ans == 1) {
            answer_is_b[current_question] = true;
        } else {
            answer_is_c[current_question] = true;
        }

        answer[current_question] = ans;
    }

    private void showQuestion() {
        String q = all_questions[current_question];
        String[] parts = q.split(":");

        group.clearCheck();

        text_question.setText(parts[0]);
        for (int i = 0; i < ids_answers.length; i++) {
            RadioButton rb = (RadioButton) activity.findViewById(ids_answers[i]);
            String ans = parts[i + 1];
            if (ans.charAt(0) == '*') {
                correct_answer = i;
                ans = ans.substring(1);
            }
            rb.setText(ans);
            if (answer[current_question] == i) {
                rb.setChecked(true);
            }
        }
        if (current_question == 0) {

            btn_prev.setVisibility(View.GONE);
        } else {
            btn_prev.setVisibility(View.VISIBLE);
        }
        if (current_question == all_questions.length - 1) {
            btn_next.setBackground(activity.getResources().getDrawable(R.drawable.aspecto_boton_rojo));
            btn_next.setText(R.string.finish);
        } else {
            btn_next.setBackground(activity.getResources().getDrawable(R.drawable.aspecto_boton_azul));
            btn_next.setText(R.string.next);
        }
    }
}
