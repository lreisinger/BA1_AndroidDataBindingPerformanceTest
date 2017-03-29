package com.ba.reisinger.ba1app;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Lukas on 28.03.2017.
 */

public class CustomTextView extends TextView {


    private long lastDrawn = 0;

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public long getLastDrawn() {
        return lastDrawn;
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        lastDrawn = System.currentTimeMillis();
        Log.i("draw", "onDrawn: " + lastDrawn);
    }

}
