package com.chernowii.spritzvr;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String text;
    public TextView left;
    public TextView right;
    public int SpritzSpeed;
    int start = 0;
    int countofwords = wordcount(text);
    String arr[] = text.split(" ", countofwords);
    String firstWord = arr[start];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//Intent text stuff

        Intent receivedIntent = getIntent();
        String receivedAction = receivedIntent.getAction();
        String receivedType = receivedIntent.getType();
        //make sure it's an action and type we can handle
        if(receivedAction.equals(Intent.ACTION_SEND)){
            text = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
            if (text != null) {
                int speed = 450;
                SpritzSpeed = speed / 60;

                left = (TextView) findViewById(R.id.left);
                left.setText(firstWord);
                right = (TextView) findViewById(R.id.right);
                right.setText(firstWord);
                handler.sendEmptyMessageDelayed(1, SpritzSpeed);
            }
        }
        else if(receivedAction.equals(Intent.ACTION_MAIN)){
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                start = start + 1;
                firstWord = arr[start];
                right.setText(firstWord);
                left.setText(firstWord);

                handler.sendEmptyMessageDelayed(1, SpritzSpeed);
            }
        }
    };
    public static int wordcount(String word) {

        if (word == null || word.trim().length() == 0) {
            return 0;
        }

        int counter = 1;

        for (char c : word.trim().toCharArray()) {
            if (c == ' ') {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
