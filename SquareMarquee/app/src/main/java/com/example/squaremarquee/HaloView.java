package com.example.squaremarquee;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Project Name:My Application
 * Create User:joe.tsai
 * Create Time:2021/2/26 10:50 AM
 * Description:
 */
public class HaloView extends View {

    private int radius;
    private int rate;
    private int origin_x;
    private int origin_y;
    private int move_x;
    private int move_y;
    private int[] position_x = new int[20];
    private int[] position_y = new int[20];
    private int startColor;
    private int endColor;

    public HaloView(Context context) {
        super(context);
    }

    public HaloView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HaloView);
        startColor = a.getColor(R.styleable.HaloView_startColor,0xFF88F13D);
        endColor = a.getColor(R.styleable.HaloView_endColor,0xFF03DAC5);
        radius = a.getInt(R.styleable.HaloView_radius,20);
        rate = a.getInt(R.styleable.HaloView_rate,20);

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                attachPaint();
            }
        },0,1);
    }

    public void attachPaint() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0 ; i < rate ; i++) {
            if (position_x[i] < (origin_x + move_x)  && position_y[i] == origin_y) {
                position_x[i] += move_x/rate;
            } else if (position_x[i] == (origin_x + move_x) && position_y[i] < (origin_y + move_y)) {
                position_y[i] += move_y/rate;
            } else if (position_x[i] > origin_x && position_y[i] == (origin_y + move_y)) {
                position_x[i] -= move_x/rate;
            } else if (position_x[i] == origin_x && position_y[i] > origin_y) {
                position_y[i] -= move_y/rate;
            }

            int alpha = (int)Math.floor(Color.alpha(startColor) + i * (Color.alpha(endColor) - Color.alpha(startColor))/rate);
            int red   = (int)Math.floor(Color.red(startColor) + i * (Color.red(endColor) - Color.red(startColor))/rate);
            int green = (int)Math.floor(Color.green(startColor) + i * (Color.green(endColor) - Color.green(startColor))/rate);
            int blue  = (int)Math.floor(Color.blue(startColor) + i * (Color.blue(endColor) - Color.blue(startColor))/rate);

            Paint currPaint = new Paint();
            currPaint.setShader(new RadialGradient(position_x[i],position_y[i], radius - (rate-i) * radius / 3 / rate,
                    Color.argb(alpha, red, green, blue), Color.TRANSPARENT, Shader.TileMode.REPEAT));
            canvas.drawCircle(position_x[i],position_y[i],radius - (rate -i) * radius / 3 / rate,currPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        move_x = measureWidth(widthMeasureSpec) - 2*radius;
        move_y = measuredHeight(heightMeasureSpec) - 2*radius;
        origin_x = radius;
        origin_y =radius;
        position_x = new int[rate];
        position_y = new int[rate];
        for (int i = 0 ; i < rate ; i++) {
            position_x[i] = origin_x + i * move_x / rate;
            position_y[i] = origin_y;
        }
        setMeasuredDimension(measureWidth(widthMeasureSpec), measuredHeight(heightMeasureSpec));
    }

    /**
     * 测量宽
     *
     * @param widthMeasureSpec
     */
    private int measureWidth(int widthMeasureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 测量高
     *
     * @param heightMeasureSpec
     */
    private int measuredHeight(int heightMeasureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
}
