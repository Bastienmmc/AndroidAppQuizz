package fr.charatweb.bastien.alphaquiz.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import fr.charatweb.bastien.alphaquiz.R;
import fr.charatweb.bastien.alphaquiz.model.Question;
import fr.charatweb.bastien.alphaquiz.model.QuestionBank;
import fr.charatweb.bastien.alphaquiz.model.User;

public class GameLogicalActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mQuestionTextView;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mNumberOfQuestions;

    private User mUser;

    private boolean mUnableTouchEvents;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionBank = this.generateQuestions();

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 4;
        }

        mUnableTouchEvents = true;

        //Wire Widget
        mQuestionTextView = (TextView) findViewById(R.id.activity_game_question_text);
        mAnswerButton1 = (Button) findViewById(R.id.activity_game_answer1_btn);
        mAnswerButton2 = (Button) findViewById(R.id.activity_game_answer2_btn);
        mAnswerButton3 = (Button) findViewById(R.id.activity_game_answer3_btn);
        mAnswerButton4 = (Button) findViewById(R.id.activity_game_answer4_btn);

        //Use the tag property to 'name' the buttons
        mAnswerButton1.setTag(0);
        mAnswerButton2.setTag(1);
        mAnswerButton3.setTag(2);
        mAnswerButton4.setTag(3);

        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            //Good Answer
            Toast.makeText(this, R.string.correct, Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            //Wrong answer
            Toast.makeText(this, R.string.wrong, Toast.LENGTH_SHORT).show();
        }

        mUnableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mUnableTouchEvents = true;
                if (--mNumberOfQuestions == 0) {
                    //End the game
                    endGame();
                } else {
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 2000);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mUnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.well_done)
                .setMessage(R.string.show_score + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //End activity
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    private void displayQuestion(final Question question) {
        mQuestionTextView.setText(question.getQuestion());
        mAnswerButton1.setText(question.getChoiceList().get(0));
        mAnswerButton2.setText(question.getChoiceList().get(1));
        mAnswerButton3.setText(question.getChoiceList().get(2));
        mAnswerButton4.setText(question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question("Terminez cette suite logique : A - D - G - ?",
                Arrays.asList("A",
                        "H",
                        "I",
                        "J"),
                3);

        Question question2 = new Question("Terminez cette suite logique : A - C - E - ?",
                Arrays.asList("A",
                        "F",
                        "G",
                        "H"),
                2);

        Question question3 = new Question("Terminez cette suite logique : A - C - B - D - C - ?",
                Arrays.asList("B",
                        "C",
                        "D",
                        "E"),
                3);

        Question question4 = new Question("Terminez cette suite logique : Z - Y - X - ?",
                Arrays.asList("V",
                        "W",
                        "X",
                        "A"),
                1);

        Question question5 = new Question("A + B + C + D = ?",
                Arrays.asList("10",
                        "4",
                        "0",
                        "aucun"),
                3);

        Question question6 = new Question("Terminez cette suite logique : AB - CD - EF - ??",
                Arrays.asList("FG",
                        "GH",
                        "HI",
                        "AG"),
                1);

        return new QuestionBank(Arrays.asList(question1, question2 , question3 , question4 , question5 , question6));
    }

}
