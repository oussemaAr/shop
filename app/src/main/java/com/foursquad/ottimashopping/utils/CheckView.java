package com.foursquad.ottimashopping.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.foursquad.ottimashopping.R;

/**
 * Created by oussemaar on 8/11/17.
 * <In Code I Trust />
 */

public class CheckView extends View implements View.OnClickListener {

    private static final String TAG = CheckView.class.getSimpleName();


    private int circleCol, labelCol;
    private String circleText;
    private Paint circlePaint;
    private boolean enabled = true;
    private boolean status = false;

    public CheckView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        circlePaint = new Paint();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CheckView, 0, 0);
        try {
            //get the text and colors specified using the names in attrs.xml
            circleText = a.getString(R.styleable.CheckView_circleLabel);
            circleCol = a.getInteger(R.styleable.CheckView_circleColor, 0);//0 is default
            labelCol = a.getInteger(R.styleable.CheckView_labelColor, 0);
            enabled = a.getBoolean(R.styleable.CheckView_enabled, true);
            status = false;
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int viewWidthHalf = this.getMeasuredWidth() / 2;
        int viewHeightHalf = this.getMeasuredHeight() / 2;
        int radius = 0;
        if (viewWidthHalf > viewHeightHalf)
            radius = viewHeightHalf - 10;
        else
            radius = viewWidthHalf - 10;
        if (!status)
            circlePaint.setStyle(Paint.Style.STROKE);
        else
            circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleCol);
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circlePaint);
        circlePaint.setColor(labelCol);
        circlePaint.setTextAlign(Paint.Align.CENTER);
        circlePaint.setTextSize(25);
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((circlePaint.descent() + circlePaint.ascent()) / 2));
        canvas.drawText(circleText, xPos, yPos, circlePaint);
    }

    @Override
    public void onClick(View view) {
        if (enabled) {
            status = !status;
            invalidate();
            requestLayout();
        }
    }

    public void setStatus(boolean status) {
        this.status = status;
        invalidate();
        requestLayout();
    }

    public boolean isStatus() {
        return status;
    }
}
