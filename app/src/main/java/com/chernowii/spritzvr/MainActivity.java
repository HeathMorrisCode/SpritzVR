package com.chernowii.spritzvr;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

String text = "";
    public TextView left;
    public TextView right;
    public int SpritzSpeed;
    int start = 0;
    int countofwords = wordcount(text);
   public String arr[] = null;
   public String firstWord = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
startEngine();

    }
void startVR(){
    AlertDialog.Builder alert = new AlertDialog.Builder(this);
    final EditText edittext = new EditText(this);
    alert.setMessage("Words Per Minute");
    alert.setTitle("Enter the SPEED");

    alert.setView(edittext);

    alert.setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {

            int speed = Integer.parseInt(edittext.getText().toString());
            SpritzSpeed = speed / 60;
            left = (TextView) findViewById(R.id.left);
            left.setText(firstWord);
            right = (TextView) findViewById(R.id.right);
            right.setText(firstWord);


        }
    });



    alert.show();
}

void startEngine(){
    String POPUP_LOGIN_TITLE="Enter details";
    final String POPUP_LOGIN_TEXT="Fill in the reading details";
    final String EMAIL_HINT="Words Per Minute";
    final String PASSWORD_HINT="Text to read";

    AlertDialog.Builder alert = new AlertDialog.Builder(this);

    alert.setTitle(POPUP_LOGIN_TITLE);
    alert.setMessage(POPUP_LOGIN_TEXT);

    // Set an EditText view to get user input
    final EditText email = new EditText(this);
    email.setHint(EMAIL_HINT);
    final EditText password = new EditText(this);
    password.setHint(PASSWORD_HINT);
    LinearLayout layout = new LinearLayout(getApplicationContext());
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.addView(email);
    layout.addView(password);
    alert.setView(layout);

    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {

            int speed = Integer.parseInt(email.getText().toString());
            SpritzSpeed = speed / 60;
            left = (TextView) findViewById(R.id.left);
            left.setText(firstWord);
            right = (TextView) findViewById(R.id.right);
            right.setText(firstWord);
            handler.sendEmptyMessageDelayed(1, SpritzSpeed);
            text = password.getText().toString();
            String arr[] = text.split(" ", countofwords);
            String firstWord = arr[start];
        }
    });

    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            // Canceled.
        }
    });

    alert.show();

}
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String arr[] = text.split(" ", countofwords);
            String firstWord = arr[start];
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
