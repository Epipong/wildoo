package com.davy.adrien.wildoo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.ViewGroup;

public class SmileyView extends SurfaceView {

    public SmileyView(Context context)
    {
        super(context);
        super.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onDraw(Canvas c)
    {
        super.onDraw(c);
        c.drawLine(0, 0, 100, 500, new Paint());
    }
}
