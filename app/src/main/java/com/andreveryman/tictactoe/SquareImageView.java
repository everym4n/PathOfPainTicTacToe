package com.andreveryman.tictactoe;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by Andrej Russkikh on 10.12.2019.
 */
public class SquareImageView extends AppCompatImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        if((wMode ==  MeasureSpec.AT_MOST || wMode == MeasureSpec.EXACTLY) && hMode == MeasureSpec.UNSPECIFIED){
            super.onMeasure(MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY));
            return;
        }
        if((hMode ==  MeasureSpec.AT_MOST || hMode == MeasureSpec.EXACTLY) && wMode == MeasureSpec.UNSPECIFIED){
            super.onMeasure(MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY));
            return;
        }

        if(wMode ==  MeasureSpec.AT_MOST && hMode ==  MeasureSpec.AT_MOST){
            int size = Math.min(width,height);
            super.onMeasure(MeasureSpec.makeMeasureSpec(size,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(size,MeasureSpec.EXACTLY));
        }

        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }
}
