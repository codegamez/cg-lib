package com.codegames.codegameslibrarytest;

import android.content.Context;
import android.graphics.*;
import android.view.View;

public class ActivityLayout extends View {

    Paint red_paintbrush_fill, blue_paintbrush_fill, green_paintbrush_fill;
    Paint red_paintbrush_stroke, blue_paintbrush_stroke, green_paintbrush_stroke;
    Path triangle;
    Bitmap cherryBitmap;
    int cherryX, cherryY;
    int xDir, yDir;
    int cherryHeight, cherryWidth;
    Path square;

    public ActivityLayout(Context context) {
        super(context);

        setBackgroundResource(R.color.color_material_amber_500);

        cherryX = 320;
        cherryY = 470;
        xDir = 1;
        yDir = 1;

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

        triangle = new Path();
        triangle.moveTo(400, 400);
        triangle.lineTo(600, 600);
        triangle.moveTo(600, 600);
        triangle.lineTo(200, 600);
        triangle.moveTo(200, 600);
        triangle.lineTo(400, 400);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect rect = new Rect();
        rect.set(210, 125, 250, 175);
        canvas.drawRect(rect, green_paintbrush_stroke);

        canvas.drawPath(triangle, red_paintbrush_stroke);

        canvas.drawCircle(400, 400, 70, blue_paintbrush_fill);
        canvas.drawCircle(400, 400, 20, green_paintbrush_fill);
        canvas.drawCircle(400, 400, 10, red_paintbrush_fill);

        canvas.drawCircle(200, 600, 70, blue_paintbrush_fill);
        canvas.drawCircle(200, 600, 20, green_paintbrush_fill);
        canvas.drawCircle(200, 600, 10, red_paintbrush_fill);

        canvas.drawCircle(600, 600, 70, blue_paintbrush_fill);
        canvas.drawCircle(600, 600, 20, green_paintbrush_fill);
        canvas.drawCircle(600, 600, 10, red_paintbrush_fill);

        cherryBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, options);
        cherryHeight = options.outHeight;
        cherryWidth = options.outWidth;

        if(cherryX >= canvas.getWidth() - cherryWidth) {
            xDir = -1;
        }

        if(cherryX <= 0) {
            xDir = +1;
        }

        if(cherryY >= canvas.getHeight() - cherryHeight) {
            yDir = -1;
        }

        if(cherryY <= 0) {
            yDir = +1;
        }

        cherryX += xDir;
        cherryY += yDir;

        canvas.drawBitmap(cherryBitmap, cherryX, cherryY, null);

        invalidate();

    }



}
