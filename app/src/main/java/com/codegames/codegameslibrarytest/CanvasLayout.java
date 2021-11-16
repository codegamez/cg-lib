package com.codegames.codegameslibrarytest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;

public class CanvasLayout extends SurfaceView {

    Timer timer = null;
    boolean canDraw = false;

    Canvas canvas;
    SurfaceHolder surfaceHolder;

    Paint red_paintbrush_fill, blue_paintbrush_fill, green_paintbrush_fill;
    Paint red_paintbrush_stroke, blue_paintbrush_stroke, green_paintbrush_stroke;

    public CanvasLayout(Context context) {
        super(context);
        surfaceHolder = getHolder();

        red_paintbrush_fill = new Paint();
        red_paintbrush_fill.setAntiAlias(true);
        red_paintbrush_fill.setColor(Color.RED);
        red_paintbrush_fill.setStyle(Paint.Style.FILL);

        blue_paintbrush_fill = new Paint();
        blue_paintbrush_fill.setAntiAlias(true);
        blue_paintbrush_fill.setColor(Color.BLUE);
        blue_paintbrush_fill.setStyle(Paint.Style.FILL);

        green_paintbrush_fill = new Paint();
        green_paintbrush_fill.setAntiAlias(true);
        green_paintbrush_fill.setColor(Color.GREEN);
        green_paintbrush_fill.setStyle(Paint.Style.FILL);

        red_paintbrush_stroke = new Paint();
        red_paintbrush_stroke.setAntiAlias(true);
        red_paintbrush_stroke.setColor(Color.RED);
        red_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        red_paintbrush_stroke.setStrokeWidth(10);

        blue_paintbrush_stroke = new Paint();
        blue_paintbrush_stroke.setAntiAlias(true);
        blue_paintbrush_stroke.setColor(Color.BLUE);
        blue_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        blue_paintbrush_stroke.setStrokeWidth(10);

        green_paintbrush_stroke = new Paint();
        green_paintbrush_stroke.setAntiAlias(true);
        green_paintbrush_stroke.setColor(Color.GREEN);
        green_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        green_paintbrush_stroke.setStrokeWidth(10);

    }

    int i = 130;
    int t = 300;

    public void draw() {

        if(!canDraw)
            return;

        if(!surfaceHolder.getSurface().isValid())
            return;

        canvas = surfaceHolder.lockCanvas();
        canvas.drawPaint(blue_paintbrush_fill);
        canvas.drawCircle(0, 0, t++, green_paintbrush_stroke);
        canvas.drawCircle(canvas.getWidth(), 0, t, green_paintbrush_stroke);
        canvas.drawCircle(canvas.getWidth(), canvas.getHeight(), t, green_paintbrush_stroke);
        canvas.drawCircle(canvas.getWidth() / 4, canvas.getHeight() / 2, t, green_paintbrush_stroke);
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, t, green_paintbrush_stroke);
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 4, t, green_paintbrush_stroke);
        canvas.drawCircle(canvas.getWidth() / 4, canvas.getHeight() / 4, t, green_paintbrush_stroke);
        canvas.drawCircle(0, canvas.getHeight(), t, green_paintbrush_stroke);
        surfaceHolder.unlockCanvasAndPost(canvas);

    }

    public void onResume() {
        canDraw = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        }, 25, 25);
    }

    public void onPause() {
        canDraw = false;

        timer.cancel();
        timer = null;
    }

    private void drawSquare(int x1, int y1, int x2, int y2) {
        Path square = new Path();

        square.moveTo(x1, y1);
        square.lineTo(x2, y1);
        square.moveTo(x2, y1);
        square.lineTo(x2, y2);
        square.moveTo(x2, y2);
        square.lineTo(x1, y2);
        square.moveTo(x1, y2);
        square.lineTo(x1, y1);

        this.canvas.drawPath(square, green_paintbrush_stroke);
    }

}
