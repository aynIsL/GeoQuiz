package ua.kyselov.geoquiz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private Button cheatButton;
    private ImageButton nextButton;
    private ImageButton previousButton;
    private TextView questionTextView;
    private int currentIndex;
    private int usedIndex;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String WAS_CHEATED = "cheated";
    private boolean isCheater;
    private boolean checkForCheatingOnNextButton;


    private TrueFalse[] questionBank = new TrueFalse[] {
        new TrueFalse(R.string.question_oceans, true),
        new TrueFalse(R.string.question_africa, false),
        new TrueFalse(R.string.question_americas, true),
        new TrueFalse(R.string.question_asia, true),
        new TrueFalse(R.string.question_mideast, false)
    };
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");

    }

    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

//    @TargetApi(22)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
            isCheater = savedInstanceState.getBoolean(WAS_CHEATED,isCheater);
        }
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            ActionBar actionBar = getActionBar();
//            actionBar.setSubtitle("TFFTT");
//        }

        Log.d(TAG, "onCreate() called");
        trueButton = (Button)findViewById(R.id.true_button);
        falseButton = (Button)findViewById(R.id.false_button);
        nextButton = (ImageButton)findViewById(R.id.next_button);
        previousButton = (ImageButton)findViewById(R.id.previous_button);
        cheatButton = (Button)findViewById(R.id.cheat_button);
        questionTextView = (TextView)findViewById(R.id.question_text_view);


        updateQuestion();


        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCheater){
                    checkForCheatingOnNextButton = true;
                    usedIndex = currentIndex;
                }
                currentIndex = (currentIndex + 1) % questionBank.length;
                if(usedIndex == currentIndex){
                    isCheater = cheatingCheck(checkForCheatingOnNextButton);
                }
                else isCheater = false;
                updateQuestion();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCheater){
                    checkForCheatingOnNextButton = true;
                    usedIndex = currentIndex;
                }
                if(currentIndex == 0)
                    currentIndex = questionBank.length-1;
                else
                    --currentIndex;
                if(usedIndex == currentIndex){
                    isCheater = cheatingCheck(checkForCheatingOnNextButton);
                }
                else isCheater = false;
                updateQuestion();
            }
        });

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = questionBank[currentIndex].isTrueQuestion();
                intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(intent, 0);
            }

        });

        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
    private void updateQuestion() {
        int question = questionBank[currentIndex].getQuestion();
        questionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = questionBank[currentIndex].isTrueQuestion();
        int messegeResId = 0;
        if(isCheater){
            messegeResId = R.string.judgment_toast;
        }
        else {
            if (userPressedTrue == answerIsTrue) {
                messegeResId = R.string.correct_toast;
            } else
                messegeResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messegeResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState called!");
        savedInstanceState.putInt(KEY_INDEX, currentIndex);
        savedInstanceState.putBoolean(WAS_CHEATED,isCheater);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null) {
            return;
        }
        isCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

    public boolean cheatingCheck(boolean isCheater){
        return isCheater;
    }
}
