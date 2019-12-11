package com.andreveryman.tictactoe;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Andrej Russkikh on 10.12.2019.
 */
public class SquareViewGroup extends FrameLayout {

    public static final String TAG = "SquareViewGroup";

    public SquareViewGroup(@NonNull Context context) {
        super(context);
    }

    public SquareViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        Log.d(TAG, "onMeasure: " + width + " "+height + " " + (wMode>>30) + " "+(hMode>>30) );
        if((wMode ==  MeasureSpec.AT_MOST || wMode == MeasureSpec.EXACTLY) && hMode == MeasureSpec.UNSPECIFIED){

            Log.d("case", "onMeasure: case 1");
            super.onMeasure(MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY));
            return;
        }
        if((hMode ==  MeasureSpec.AT_MOST || hMode == MeasureSpec.EXACTLY) && wMode == MeasureSpec.UNSPECIFIED){
            Log.d("case", "onMeasure: case 2");
            super.onMeasure(MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY));
            return;
        }

        if(wMode ==  MeasureSpec.AT_MOST && hMode ==  MeasureSpec.AT_MOST){
            Log.d("case", "onMeasure: case 3");
            int size = Math.min(width,height);
            super.onMeasure(MeasureSpec.makeMeasureSpec(size,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(size,MeasureSpec.EXACTLY));
        }

        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }
}
