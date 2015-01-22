/*
 * Copyright (c) 2015 Marine Carrara, Akana Mao, Randy Ratsimbazafy
 *
 * This file is part of Tracer c'est gagné.
 *
 * Tracer c'est gagné is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tracer c'est gagné is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Tracer c'est gagné.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.m2dl.challenge.com.m2dl.views;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.m2dl.challenge.challenge.ActivityAcceptAnswer;

import com.m2dl.challenge.challenge.R;
import com.m2dl.challenge.core.Scoring;

/**
 * Created by marine on 22/01/15.
 */
public class AnswerDialog extends Dialog implements android.view.View.OnClickListener {

    private ActivityAcceptAnswer activity;
    private Button yes, cancel;
    private String answer;

    public AnswerDialog(ActivityAcceptAnswer activity) {
        super(activity);

        answer = "";
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.correct_answer_dialog);
        yes = (Button) findViewById(R.id.btn_answer_ok);
        cancel = (Button) findViewById(R.id.btn_answer_cancel);
        yes.setOnClickListener(this);
        cancel.setOnClickListener(this);
        this.setTitle(R.string.answerDialogTitle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_answer_ok:
                EditText answerEditText = (EditText) findViewById(R.id.editCorrectAnswer);
                answer = answerEditText.getText().toString();
                dismiss();
                activity.restartGame("Faux ! La réponse était : " + answer, Scoring.SCORE_LOOSE);
                break;
            case R.id.btn_answer_cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();

    }

    public String getAnswer() {
        return answer;
    }
}