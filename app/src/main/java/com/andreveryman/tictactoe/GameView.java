package com.andreveryman.tictactoe;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Andrej Russkikh on 10.12.2019.
 */
public class GameView extends FrameLayout {

    public static final int SIZE = 3;
    private TicTacToeField gameLogicProvider;
    private GameAdapter adapter;
    private RecyclerView recyclerView;


    public GameView(@NonNull Context context) {
        this(context, null);
    }

    public GameView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        {
            recyclerView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.recycler_view_layout, this, false);
            addView(recyclerView);
            init();
        }
    }

    private void init() {
        if (gameLogicProvider == null)
            gameLogicProvider = new TicTacToeField(SIZE);
        adapter = new GameAdapter(gameLogicProvider, SIZE);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), SIZE));
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


    public void reset() {
        adapter.reset();


    }

    public void setOnGameEndListener(GameEndedListener listener) {
        adapter.setListener(listener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        if ((wMode == MeasureSpec.AT_MOST || wMode == MeasureSpec.EXACTLY) && hMode == MeasureSpec.UNSPECIFIED) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST));
            return;
        }
        if ((hMode == MeasureSpec.AT_MOST || hMode == MeasureSpec.EXACTLY) && wMode == MeasureSpec.UNSPECIFIED) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
            return;
        }
        if ((wMode == MeasureSpec.AT_MOST || wMode == MeasureSpec.EXACTLY)
                && (hMode == MeasureSpec.EXACTLY || hMode == MeasureSpec.AT_MOST)) {
            int size = Math.min(width, height);
            super.onMeasure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(size, MeasureSpec.AT_MOST));
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        TicTacToeSavedState state = new TicTacToeSavedState(super.onSaveInstanceState());
        state.gameLogicProvider = this.gameLogicProvider;

        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        TicTacToeSavedState restoredState = (TicTacToeSavedState) state;
        gameLogicProvider = restoredState.gameLogicProvider;
        adapter.setGameLogicProvider(gameLogicProvider);
        super.onRestoreInstanceState(((TicTacToeSavedState) state).getSuperState());

    }

    private static class TicTacToeSavedState extends BaseSavedState implements Parcelable {
        private TicTacToeField gameLogicProvider;

        public TicTacToeSavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public TicTacToeSavedState(Parcel source) {
            super(source);
            gameLogicProvider = CREATOR.createFromParcel(source).gameLogicProvider;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(gameLogicProvider, flags);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<TicTacToeSavedState> CREATOR = new Creator<TicTacToeSavedState>() {
            @Override
            public TicTacToeSavedState createFromParcel(Parcel in) {
                return new TicTacToeSavedState(in);
            }

            @Override
            public TicTacToeSavedState[] newArray(int size) {
                return new TicTacToeSavedState[size];
            }
        };
    }
}
