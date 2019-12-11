package com.andreveryman.tictactoe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements GameListener{


    public static final String KEY_TIC_TAC = "tictac";
    public static final int SIZE = 3;
    TicTacToeField ticTacToeField = new TicTacToeField(SIZE);
    private TicTacToeAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            ticTacToeField = savedInstanceState.getParcelable(KEY_TIC_TAC);

        }
        if(ticTacToeField == null)
            ticTacToeField = new TicTacToeField(SIZE);

        setContentView(R.layout.activity_main);
        adapter = new TicTacToeAdapter(ticTacToeField,SIZE, this);
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this,SIZE));
        rv.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL);
        rv.addItemDecoration(dividerItemDecoration);
        dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(dividerItemDecoration);

        findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               reset();
            }
        });
    }



    @Override
    public void gameEnded(TicTacToeField.Figure figure) {
        int message = 0;
        if(figure == TicTacToeField.Figure.CIRCLE)
            message = R.string.zero_wins;
        if(figure == TicTacToeField.Figure.CROSS)
            message = R.string.cross_wins;
        if(figure == TicTacToeField.Figure.NONE)
            message = R.string.nobody_wins;

        new AlertDialog.Builder(this).setTitle(R.string.game_over).setMessage(message)
                .setPositiveButton(R.string.confirm,null).setNegativeButton(R.string.again, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                reset();
            }
        }).show();
    }

    public void reset(){
        adapter.reset();
        adapter.notifyDataSetChanged();

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(KEY_TIC_TAC,ticTacToeField);
        super.onSaveInstanceState(outState);

    }
}
