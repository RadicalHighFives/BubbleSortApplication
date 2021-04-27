package com.example.cpsc544;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.text.BreakIterator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText numberInput;
    Button submitButton;
    String num;
    TextView textView1;
    boolean validInput;
    private Handler handler = new Handler();
    int arrayLength;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView)findViewById(R.id.textView4);
        numberInput = (EditText) findViewById(R.id.numText);
        submitButton = (Button) findViewById(R.id.enterButton);

        // ===== Check user input as they type
        numberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int inputLength = numberInput.getText().toString().length();
                char[] charArray = s.toString().toCharArray();
                if(inputLength != 0){
                    if(CheckCharError(charArray)){
                        numberInput.setError("Reject non-integer");
                    validInput = false;
                    }
                    else{
                        validInput = true;
                    }

                }
            }

        });

        // ==== Submit button onClick event Listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validInput){
                    return;
                }
                // ===== Set text Movement to Scroll
                textView1.setMovementMethod(new ScrollingMovementMethod());

                // ===== Get input and convert to string
                num = numberInput.getText().toString();

                // ===== Split into an str array
                String[] numItems = num.split(" ");
                int [] numArray = new int[numItems.length];
                arrayLength = numItems.length;

                // ===== Remove spaces in string using regex
                String removeSpaces = num.replaceAll("\\s+","");
                char []chars = removeSpaces.toCharArray();

                // ===== Check length and number of digits
                if(chars.length < 3){
                    numberInput.setError("The input you have submitted has not met the minimum number of characters.");
                }
                else if(chars.length > 8) {
                    numberInput.setError("The input you have submitted has exceeded the maximum number of allowed characters.");
                }
                else if (CheckDigitSize(numItems)) {
                    numberInput.setError("The input contains integers too big to be sorted.");
                }
                else {
                    for (int i = 0; i < numItems.length; i++) {
                        numArray[i] = Integer.parseInt(numItems[i]);
                    }

                    // ===== Begin Sorting return Hash<key, line>
                    Map<Integer,String> mappedBubbleSortLines = BubbleSort(numArray);

                    // ===== Update half second
                    int seconds = 500;
                    long endTime = mappedBubbleSortLines.size()*seconds;
                    for (int k : mappedBubbleSortLines.keySet()){
                        String lineRow = mappedBubbleSortLines.get(k).trim();
                        handler.postDelayed(new TextViewUpdate(lineRow,textView1),seconds);
                        seconds = seconds+500;
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // ===== Finish Sorting
                            AlertPrompt();
                        }
                    },endTime);
                }
            }
        });
    }

    boolean CheckCharError(char[] chars){
        boolean checkUnwantedChar = false;
        for (int j = 0; j < chars.length; j++) {
            if( !(chars[j] >= '0' && chars[j] <= '9') && chars[j] != ' '){
                checkUnwantedChar = true;
            }
        }
        return checkUnwantedChar;
    }

    boolean CheckDigitSize(String[] nums){
        boolean checkExtraDigits = false;
        for (int j = 0; j < nums.length; j++) {
            if(nums[j].length() > 1){
                checkExtraDigits = true;
            }
        }
        return checkExtraDigits;
    }

    protected Map<Integer,String> BubbleSort(int [] array){
        // ===== O(n^2) time complexity
        // ===== Loop through each comparison starting at the last index
        Map<Integer,String> storeLines = new HashMap<Integer, String>();
        String appendLine = "";
        int index = 0;
        for (int i = array.length; i > 0; i--)
        {
            for (int j = array.length - 1; j > 0; j--)
            {
                if (array[j-1] > array[j])
                {
                    // ===== Swap values next to current value
                    int temp = array[j-1];
                    array[j-1] = array[j];
                    array[j] = temp;
                }
                for (int item: array ) {
                    // ===== set Text View for each iteration
                    appendLine += String.valueOf(item)+ " ";
                }
                // ===== Store key value (line) and Clear line after
                storeLines.put(index++,appendLine);
                appendLine = "";
            }
            // ======== Space Between Iterations
            //textView1.append("\n");
        }
        return storeLines;
    }

    public int[] BubbleSortTest(int [] array){
        // ===== O(n^2) time complexity
        // ===== Loop through each comparison starting at the last index
        for (int i = array.length; i > 0; i--)
        {
            for (int j = array.length - 1; j > 0; j--)
            {
                for (int item: array ) {
                    // ===== set Text View for each iteration
                    System.out.print(item);
                }
                if (array[j-1] > array[j])
                {
                    // Swap values next to current value
                    int temp = array[j-1];
                    array[j-1] = array[j];
                    array[j] = temp;
                }
                // ===== Next line
                System.out.println("\n");
            }
        }
        return array;
    }

    public void exitAppClick(View view) {
        finishAffinity();
        System.exit(0);
    }

    private void AlertPrompt(){

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.smiley);
        dialog.setMessage("The bubbles have been sorted!");
        dialog.setTitle("Message:");
        dialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        //Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                    }
                }).setView(image);
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    public void clearTextClick(View view){
        numberInput.setText("");
        textView1.setText("");
    }

    private class TextViewUpdate implements Runnable{
        private String mString;
        private TextView mView;

        public TextViewUpdate(String s, TextView tv){
            mString = s;
            mView = tv;
        }

        @Override
        public void run(){
            mView.append(mString);
            mView.append("\n");
        }
    }
}
