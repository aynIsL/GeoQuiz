package ua.kyselov.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import junit.framework.TestCase;

/**
 * Created by Ivan on 05.11.2015.
 */
public class CheatActivity extends Activity {

    private static final String TAG = "CheatActivity";
    public static final String EXTRA_ANSWER_IS_TRUE ="ua.kyselov.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "ua.kyselov.answer_is_shown";
    private static final String EXTRA_CHEATED = "cheated";
    private TextView answerTextView;
    private TextView apiLevel;
    private Button showAnswer;
    private boolean isCheated;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
            isCheated = savedInstanceState.getBoolean(EXTRA_CHEATED);
        setContentView(R.layout.activity_cheat);
        setAnswerShownResult(false);
        final boolean answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        answerTextView = (TextView)findViewById(R.id.answer_text_view);
        showAnswer = (Button)findViewById(R.id.show_answer);
        apiLevel = (TextView)findViewById(R.id.api_level);

        apiLevel.setText("API Level" + Build.VERSION.SDK_INT);

        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answerIsTrue) {
                    answerTextView.setText(R.string.true_button);
                } else {
                    answerTextView.setText(R.string.false_button);
                }
                isCheated = true;
                setAnswerShownResult(isCheated);
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState called!");
        savedInstanceState.putBoolean(EXTRA_CHEATED, isCheated);
    }


    public void onDestroy(){
        super.onDestroy();
        setAnswerShownResult(isCheated);
    }
}
