package com.fanyafeng.recreation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.fanyafeng.recreation.util.MyUtils;


/**
 * Created by 365rili on 16/4/20.
 */
public class CircleLoadingView extends View {

    private Paint circleContour;
    private double durSpace = 0;

    private float heightLength;
    private float widthLength;

    private CharSequence message;
    private StaticLayout messageLayout;
    private static TextPaint textPaint;

    private int circleContourColor;
    private int messageColor;

    public float getHeightLength() {
        return heightLength;
    }

    public void setHeightLength(float heightLength) {
        this.heightLength = heightLength;
    }

    public float getWidthLength() {
        return widthLength;
    }

    public void setWidthLength(float widthLength) {
        this.widthLength = widthLength;
    }

    public int getCircleContourColor() {
        return circleContourColor;
    }

    public void setCircleContourColor(int circleContourColor) {
        this.circleContourColor = circleContourColor;
        invalidate();
    }

    public int getMessageColor() {
        return messageColor;
    }

    public void setMessageColor(int messageColor) {
        this.messageColor = messageColor;
        setMessage(getMessage());
    }

    public CharSequence getMessage() {
        return message;
    }

    public void setMessage(CharSequence message) {
        this.message = message;
        if (message != null && !message.equals("")) {
            textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            if (getMessageColor() != 0) {
                textPaint.setColor(getMessageColor());
            } else {
                textPaint.setColor(Color.parseColor("#3498DB"));
            }
            textPaint.setTextSize(14 * MyUtils.getDensity(getContext()));
            textPaint.setStyle(Paint.Style.FILL);
            messageLayout = new StaticLayout(message, textPaint, MyUtils.getScreenWidth(getContext()) / 4, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
            invalidate();
        }

    }

    public CircleLoadingView(Context context) {
        super(context);
        init();
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circleContour = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleContour.setAntiAlias(true);
        if (getCircleContourColor() != 0) {
            circleContour.setColor(getCircleContourColor());
        } else {
            circleContour.setColor(Color.parseColor("#3498DB"));
        }
        circleContour.setStrokeWidth(circleContourStrokeWidth);
    }

    static float circleContourStrokeWidth = 5;
    static float circleCountourX;
    static float circleCountourY;
    static float height;
    static float width;
    static float circleContourRadius;
    static double everyRadiusLength = 2 * Math.PI / 360;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (height == 0) {
            height = heightLength == 0 ? getHeight() : heightLength;
        }

        if (width == 0) {
            width = widthLength == 0 ? getWidth() : widthLength;
        }
        if (circleContourRadius == 0) {
            circleContourRadius = (height > width ? width / 2 - circleContourStrokeWidth / 2 : height / 2 - circleContourStrokeWidth / 2);
        }
        if (width > height) {
            circleCountourX = width / 2;
            circleCountourY = height / 2;
        } else {
            circleCountourX = circleCountourY = width / 2;
        }

        if (everyRadiusLength == 0) {
            everyRadiusLength = 2 * Math.PI / 360;
        }


        double initAngle = 345;
        double initRadius = 3.75;
        for (int i = 0; i < 8; i++) {
            double temp = 0;
            double total = 0;
            for (int j = 0; j <= i; j++) {
                temp = j * 7.5;
                total += temp;
            }
            double itemAngle = durSpace + (initAngle - (total + i * 15));
            double itemX = circleCountourX - 2 * circleContourRadius / 3 * Math.sin(itemAngle * everyRadiusLength);
            double itemY = circleCountourY - 2 * circleContourRadius / 3 * Math.cos(itemAngle * everyRadiusLength);
            double itemRadius = 2 * circleContourRadius / 3 * Math.sin((initRadius + i * 3.75) * everyRadiusLength);
            canvas.drawCircle((float) itemX, (float) itemY, (float) itemRadius, circleContour);
        }

        canvas.save();
        canvas.translate(0, 2 * circleContourRadius + 50);
        messageLayout.draw(canvas);
        canvas.restore();

        postDelayed(refresh, 37);
    }

    private Runnable refresh = new Runnable() {
        @Override
        public void run() {
            durSpace -= 3;
            invalidate();
        }
    };

}
