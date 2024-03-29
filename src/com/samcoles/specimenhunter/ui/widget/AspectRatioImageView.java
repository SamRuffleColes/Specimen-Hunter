package com.samcoles.specimenhunter.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AspectRatioImageView extends ImageView {

    public AspectRatioImageView(Context context) {
        super(context);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        
        if(width < height) height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
        else width = height * getDrawable().getIntrinsicWidth() / getDrawable().getIntrinsicHeight();
        
        setMeasuredDimension(width, height);
    }
}