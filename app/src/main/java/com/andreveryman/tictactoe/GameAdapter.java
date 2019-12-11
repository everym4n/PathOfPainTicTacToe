package com.andreveryman.tictactoe;

import android.graphics.drawable.Drawable;
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
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.TicTacHolder> {

    private TicTacToeField gameLogicProvider;
    private int size;
    private int row;
    private int turn = 0;
    private GameEndedListener listener;



    public GameAdapter(TicTacToeField ticTacToeField, int size) {
        this.gameLogicProvider = ticTacToeField;
        this.row = size;
        this.size = size*size;
    }


    public void reset() {
        turn = 0;
        gameLogicProvider.reset();
        notifyItemRangeChanged(0,size);
    }

    public void setListener(GameEndedListener listener) {
        this.listener = listener;
    }

    public void setGameLogicProvider(TicTacToeField gameLogicProvider){
        this.gameLogicProvider = gameLogicProvider;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TicTacHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TicTacHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.field, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TicTacHolder holder, final int position) {

        final TicTacToeField.Figure figure = gameLogicProvider.getFigure(getRow(position), getColumn(position));
        Drawable drawable = null;
        if (figure == TicTacToeField.Figure.CIRCLE)
            drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.circle);
        else if (figure == TicTacToeField.Figure.CROSS)
            drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cross);

        holder.image.setImageDrawable(drawable);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (figure != TicTacToeField.Figure.NONE || gameLogicProvider.isFull() || TicTacToeField.Figure.NONE != gameLogicProvider.getWinner())
                    return;
                turn = (turn + 1) % 2;
                TicTacToeField.Figure newFigure;
                if (turn == 0)
                    newFigure = TicTacToeField.Figure.CROSS;
                else
                    newFigure = TicTacToeField.Figure.CIRCLE;
                gameLogicProvider.setFigure(getRow(position), getColumn(position), newFigure);
                notifyItemChanged(position);
                checkIfGameEnded();


            }
        });

    }


    private void checkIfGameEnded() {
        TicTacToeField.Figure winner = gameLogicProvider.getWinner();

        if (winner == TicTacToeField.Figure.NONE) {
            if (gameLogicProvider.isFull()) {
                postResult(TicTacToeField.Figure.NONE);
            }
            return;
        } else {
            if (winner == TicTacToeField.Figure.CIRCLE) {
                postResult(TicTacToeField.Figure.CIRCLE);
            } else {
                postResult(TicTacToeField.Figure.CROSS);
            }
        }
    }


    private void postResult(TicTacToeField.Figure result){
        if(listener!=null)
            listener.gameEnded(result);
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
            image =  (ImageView) itemView;
        }
    }
}
