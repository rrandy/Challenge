package com.m2dl.challenge.challenge;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.m2dl.challenge.core.DataBluetooth;


public class ActivityProposeAnswer extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_propose_answer);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_answer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendAnswer (View v) {

        EditText answerEdit = (EditText) findViewById(R.id.editAnswer);
        String answerToSend = answerEdit.getText().toString();


        if (answerToSend != null && !answerToSend.equals("")) {
            DataBluetooth bluetooth = new DataBluetooth();

            // Send the answer
            bluetooth.sendDataByString(getResources().getString(R.string.keyGuessAnswer), answerToSend);

            // Wait for the reply (correct/fail) and give a feedback to the player
            String reply = bluetooth.getData(getResources().getString(R.string.keyCorrectAnswer));
            Toast.makeText(getApplicationContext(), reply, Toast.LENGTH_LONG).show();

            // Go back to the first activity
            Intent intent = new Intent(ActivityProposeAnswer.this, ActivityMainMenu.class);
            startActivity(intent);
        }
    }
}
