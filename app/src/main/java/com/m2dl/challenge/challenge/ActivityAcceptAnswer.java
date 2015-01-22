package com.m2dl.challenge.challenge;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.m2dl.challenge.com.m2dl.views.AnswerDialog;
import com.m2dl.challenge.core.DataBluetooth;


public class ActivityAcceptAnswer extends ActionBarActivity {

    private DataBluetooth bluetooth;
    private AnswerDialog dialog;
    private String messageToReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_accept_answer);

        bluetooth = new DataBluetooth();

        // Get the submitted anwser
        String answerReceived = bluetooth.getData(getResources().getString(R.string.keyGuessAnswer));
        TextView answerToShow = (TextView) findViewById(R.id.tvAnswerReceived);
        answerToShow.setText(answerReceived);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_accept_answer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void acceptAnswer(View v) {
        messageToReply = "Correct !";
        int scoreJ2 = 1;
        restartGame(messageToReply, scoreJ2);
    }

    public void refuseAnswer(View v) {
        dialog = new AnswerDialog(this);
        dialog.setContentView(R.layout.correct_answer_dialog);
        dialog.show();
    }

    public void restartGame (String messageToReply, int newScoreForJ2) {
        // Update scores

        Toast.makeText(getApplicationContext(),messageToReply,Toast.LENGTH_LONG).show();

        // Send feedback to the other player
        bluetooth.sendDataByString(getResources().getString(R.string.keyCorrectAnswer), messageToReply);

        // Go back to the first activity
        Intent intent = new Intent(ActivityAcceptAnswer.this, ActivityMainMenu.class);
        startActivity(intent);
    }
}
