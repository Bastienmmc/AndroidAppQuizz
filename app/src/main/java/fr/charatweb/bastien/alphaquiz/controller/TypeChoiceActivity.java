package fr.charatweb.bastien.alphaquiz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.charatweb.bastien.alphaquiz.R;

public class TypeChoiceActivity extends AppCompatActivity {

    private TextView mChooseType;
    private Button mMathButton;
    private Button mColorsButton;
    private Button mWordsButton;
    private Button mLogicalButton;
    private Button mGoBackButton;

    private SharedPreferences mPreferences;

    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;

    public static final String PREF_KEY_LAST_THEME = "PREF_KEY_LAST_THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_choice);

        mChooseType     = (TextView) findViewById(R.id.choose_theme_txt);
        mMathButton     = (Button) findViewById(R.id.choose_theme_math_btn);
        mColorsButton   = (Button) findViewById(R.id.choose_theme_colors_btn);
        mWordsButton    = (Button) findViewById(R.id.choose_theme_words_btn);
        mLogicalButton  = (Button) findViewById(R.id.choose_theme_logical_btn);
        mGoBackButton   = (Button) findViewById(R.id.go_back_btn);

        mPreferences = getPreferences(MODE_PRIVATE);

        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mMathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPreferences.edit().putString(PREF_KEY_LAST_THEME, "math").apply();

                Intent gameMathActivity = new Intent(TypeChoiceActivity.this, GameMathActivity.class);
                startActivityForResult(gameMathActivity, GAME_ACTIVITY_REQUEST_CODE);
            }
        });

        mLogicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreferences.edit().putString(PREF_KEY_LAST_THEME, "logical").apply();

                Intent gameLogicalActivity = new Intent(TypeChoiceActivity.this, GameLogicalActivity.class);
                startActivityForResult(gameLogicalActivity, GAME_ACTIVITY_REQUEST_CODE);
            }
        });


    }
}
