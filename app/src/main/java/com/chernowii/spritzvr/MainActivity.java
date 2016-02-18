package com.chernowii.spritzvr;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
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
        Intent receivedIntent = getIntent();
        String receivedAction = receivedIntent.getAction();
        //make sure it's an action and type we can handle
        if(receivedAction.equals(Intent.ACTION_SEND)){
            String receivedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
            //check we have a string
            if (receivedText != null) {
                //set the text
                startEngine(receivedText);
            }

        }
        else if(receivedAction.equals(Intent.ACTION_MAIN)){
            startEngine("input");        }

    }


void startEngine(final String vrtext){
    String Details="Enter details";
    final String Fill="Fill in the reading details";
    final String WORDS_PER_MINUTE="Text speed";
    final String TEXT_2_READ="Text to read";

    AlertDialog.Builder alert = new AlertDialog.Builder(this);

    alert.setTitle(Details);
    alert.setMessage(Fill);

    // Set an EditText view to get user input
    final CheckBox wpm_fast = new CheckBox(this);
    wpm_fast.setHint("Fast");
    final CheckBox wpm_medium = new CheckBox(this);
    wpm_medium.setHint("Medium");
    final CheckBox wpm_slow = new CheckBox(this);
    wpm_slow.setHint("Slow");
    final EditText textbox = new EditText(this);
    if(vrtext.equals("input")){
        textbox.setHint(TEXT_2_READ);
    }
    else{
        textbox.setVisibility(View.INVISIBLE);
    }

    LinearLayout layout = new LinearLayout(getApplicationContext());
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.addView(wpm_fast);
    layout.addView(wpm_medium);
    layout.addView(wpm_slow);
    layout.addView(textbox);
    alert.setView(layout);

    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {

            boolean isSlowChecked = wpm_slow.isChecked();
            boolean isMediumChecked = wpm_medium.isChecked();
            boolean isFastChecked = wpm_fast.isChecked();
            if (isSlowChecked){
                SpritzSpeed = 9000 / 60;
            }
            if (isMediumChecked){
                SpritzSpeed = 6000 / 60;
            }
            if (isFastChecked){
                SpritzSpeed = 4000 / 60;
            }
            left = (TextView) findViewById(R.id.left);
            left.setText(firstWord);
            right = (TextView) findViewById(R.id.right);
            right.setText(firstWord);
            handler.sendEmptyMessageDelayed(1, SpritzSpeed);
            if(vrtext.equals("input")){
                text = textbox.getText().toString();
                String arr[] = text.split(" ", countofwords);
                firstWord = arr[start];
            }
            else{
                text = vrtext;
                textbox.setVisibility(View.VISIBLE);
                String arr[] = text.split(" ", countofwords);
                firstWord = arr[start];
            }

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
