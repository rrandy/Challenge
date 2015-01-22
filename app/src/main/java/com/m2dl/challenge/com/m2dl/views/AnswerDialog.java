package com.m2dl.challenge.com.m2dl.views;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.m2dl.challenge.challenge.ActivityAcceptAnswer;

import com.m2dl.challenge.challenge.R;

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
                activity.restartGame("Faux ! La réponse était : " + answer, 0);
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