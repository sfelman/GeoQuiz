package spc.android.geoquiz;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private Button mTrueButton, mFalseButton;
    private ImageButton mNextButton, mPreviousButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;

    private Question[] mQuestionBank = new Question[] {
        new Question(R.string.question_oceans, true),
        new Question(R.string.question_mideast, false),
        new Question(R.string.question_africa, false),
        new Question(R.string.question_americas, true),
        new Question(R.string.question_asia, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        if(savedInstanceState !=null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex++;
                if(mCurrentIndex==mQuestionBank.length) {
                    mNextButton.setEnabled(false);
                    mTrueButton.setEnabled(false);
                    mFalseButton.setEnabled(false);
                    mQuestionTextView.setEnabled(false);
                    mQuestionTextView.setText(R.string.out_of_questions);
                }
                else {
                    updateQuestion();
                    if (mPreviousButton.isEnabled() == false) {
                        mPreviousButton.setEnabled(true);
                    }
                }
            }
        });
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setEnabled(false);
        mPreviousButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mNextButton.isEnabled()==false){
                    mNextButton.setEnabled(true);
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                    mQuestionTextView.setEnabled(true);
                }
                mCurrentIndex--;
                updateQuestion();
                if(mCurrentIndex == 0)mPreviousButton.setEnabled((false));
            }
        });
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex++;
                if(mCurrentIndex==mQuestionBank.length) {
                    mNextButton.setEnabled(false);
                    mTrueButton.setEnabled(false);
                    mFalseButton.setEnabled(false);
                    mQuestionTextView.setText(R.string.out_of_questions);
                }
                else {
                    updateQuestion();
                    if (mPreviousButton.isEnabled() == false) {
                        mPreviousButton.setEnabled(true);
                    }
                }
            }
        });
        if(mCurrentIndex !=mQuestionBank.length) {
            updateQuestion();
        }
        else{
            mNextButton.setEnabled(false);
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
            mQuestionTextView.setEnabled(false);
            mQuestionTextView.setText("There are no more questions for you to answer.");
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState() called");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }
    private void checkAnswer(boolean userResponse){
        boolean answer = mQuestionBank[mCurrentIndex].isAnswerTrue();
        //more optimized than code in book, gets rid of unnecessary messageResId variable
        if(userResponse == answer) Toast.makeText(this,R.string.correct_toast, Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
    }
}
