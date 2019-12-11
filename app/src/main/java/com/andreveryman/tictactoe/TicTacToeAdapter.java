package com.andreveryman.tictactoe;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Andrej Russkikh on 10.12.2019.
 */
public class TicTacToeAdapter extends RecyclerView.Adapter<TicTacToeAdapter.TicTacHolder> {

    private TicTacToeField ticTacToeField;
    private int size;
    private int row;
    private int turn = 0;
    private GameListener listener;



    public TicTacToeAdapter(TicTacToeField ticTacToeField, int size, GameListener listener) {
        this.ticTacToeField = ticTacToeField;
        this.row = size;
        this.size = size*size;
        this.listener = listener;
    }


    public void reset() {
        turn = 0;
        ticTacToeField.reset();
    }


    @NonNull
    @Override
    public TicTacHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TicTacHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.field, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TicTacHolder holder, final int position) {

        Log.d("TAG", "position = " + position + " " + getRow(position) + " " + getColumn(position));
        final TicTacToeField.Figure figure = ticTacToeField.getFigure(getRow(position), getColumn(position));

        Drawable drawable = null;
        if (figure == TicTacToeField.Figure.CIRCLE)
            drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.circle);
        else if (figure == TicTacToeField.Figure.CROSS)
            drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cross);

        holder.image.setImageDrawable(drawable);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (figure != TicTacToeField.Figure.NONE || ticTacToeField.isFull() || TicTacToeField.Figure.NONE != ticTacToeField.getWinner())
                    return;
                turn = (turn + 1) % 2;
                TicTacToeField.Figure newFigure;
                if (turn == 0)
                    newFigure = TicTacToeField.Figure.CROSS;
                else
                    newFigure = TicTacToeField.Figure.CIRCLE;
                ticTacToeField.setFigure(getRow(position), getColumn(position), newFigure);
                notifyItemChanged(position);
                checkIfGameEnded();


            }
        });

    }


    private void checkIfGameEnded() {
        TicTacToeField.Figure winner = ticTacToeField.getWinner();

        if (winner == TicTacToeField.Figure.NONE) {
            if (ticTacToeField.isFull()) {
                listener.gameEnded(TicTacToeField.Figure.NONE);
            }
            return;
        } else {
            if (winner == TicTacToeField.Figure.CIRCLE) {
                listener.gameEnded(TicTacToeField.Figure.CIRCLE);
            } else {
                listener.gameEnded(TicTacToeField.Figure.CROSS);
            }
        }
    }


    private int getRow(int position) {
        return position / row;
    }

    private int getColumn(int position) {
        return position % row;
    }


    @Override
    public int getItemCount() {
        return size;
    }


    static class TicTacHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public TicTacHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView;
        }
    }
}
