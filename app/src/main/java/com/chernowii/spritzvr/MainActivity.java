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

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }

        }


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    void handleSendText(Intent intent) {
        text = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (text != null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText edittext = new EditText(this);
            alert.setMessage("Words Per Minute");
            alert.setTitle("Enter the SPEED");

            alert.setView(edittext);

            alert.setPositiveButton("LAUNCH", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    int speed = Integer.parseInt(edittext.getText().toString());
                    SpritzSpeed = speed / 60;
                    Toast.makeText(getApplicationContext(),SpritzSpeed,Toast.LENGTH_SHORT).show();
                    left = (TextView) findViewById(R.id.left);
                    left.setText(firstWord);
                    right = (TextView) findViewById(R.id.right);
                    right.setText(firstWord);
                    handler.sendEmptyMessageDelayed(1, SpritzSpeed);

                }
            });



            alert.show();
        }
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
