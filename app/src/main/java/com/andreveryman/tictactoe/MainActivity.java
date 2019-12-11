package com.andreveryman.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements GameEndedListener {


    public static final String ZERO_WIN_COUNT_KEY = "zeroWinCount";
    public static final String CROSS_WIN_COUNT_KEY = "crossWinCount";
    public static final String RESULT_SHOWN_KEY = "showResult";
    private static final String RESULT_TEXT_KEY = "resultText";

    private View resultLayout;
    private TextView resultText;
    private GameView gameView;
    private int zeroWinCount;
    private int crossWinCount;
    private TextView textViewZeroWins;
    private TextView textViewCrossWins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultLayout = findViewById(R.id.result_layout);
        resultText = findViewById(R.id.tv_result_message);
        gameView = findViewById(R.id.ticTacToe);
        gameView.setOnGameEndListener(this);
        textViewZeroWins = findViewById(R.id.tv_zero_wins);
        textViewCrossWins = findViewById(R.id.tv_cross_wins);


        restoreState(savedInstanceState);



        findViewById(R.id.btn_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.reset();
                hideResult();
            }
        });
        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void updateCount(){
        textViewZeroWins.setText(String.format(getResources().getString(R.string.number_format),zeroWinCount));
        textViewCrossWins.setText(String.format(getResources().getString(R.string.number_format),crossWinCount));

    }



    @Override
    public void gameEnded(TicTacToeField.Figure figure) {
        int message = 0;
        if(figure == TicTacToeField.Figure.CIRCLE){
            zeroWinCount++;
            message = R.string.zero_wins;
        }
        if(figure == TicTacToeField.Figure.CROSS){
            crossWinCount++;
            message = R.string.cross_wins;
        }
        updateCount();
        if(figure == TicTacToeField.Figure.NONE)
            message = R.string.nobody_wins;
        resultText.setText(message);
        showResult();
    }

    private void showResult(){
        resultLayout.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.slide_in_bottom);
        animation.setInterpolator(new OvershootInterpolator());
        resultLayout.startAnimation(animation);

    }

    private void hideResult(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.slide_out_bottom);

        resultLayout.startAnimation(animation);
        resultLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(ZERO_WIN_COUNT_KEY,zeroWinCount);
        outState.putInt(CROSS_WIN_COUNT_KEY,crossWinCount);
        outState.putBoolean(RESULT_SHOWN_KEY,resultLayout.getVisibility() == View.VISIBLE);
        outState.putString(RESULT_TEXT_KEY, resultText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        if(savedInstanceState == null)
            return;
        zeroWinCount = savedInstanceState.getInt(ZERO_WIN_COUNT_KEY,0);
        crossWinCount = savedInstanceState.getInt(CROSS_WIN_COUNT_KEY,0);
        resultText.setText(savedInstanceState.getString(RESULT_TEXT_KEY,""));
        if(savedInstanceState.getBoolean(RESULT_SHOWN_KEY,false)){
            resultLayout.setVisibility(View.VISIBLE);
        }else{
            resultLayout.setVisibility(View.INVISIBLE);
        }
        updateCount();
    }
}
