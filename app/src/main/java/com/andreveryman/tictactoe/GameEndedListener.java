package com.andreveryman.tictactoe;

/**
 * Created by Andrej Russkikh on 10.12.2019.
 */
public interface GameEndedListener {
    void gameEnded(TicTacToeField.Figure winner);

}
