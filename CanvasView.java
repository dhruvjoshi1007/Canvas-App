package com.example.dhruv.canvasdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View
{
    public int width,height;
    private Bitmap bitmap;
    private Canvas canvas;
    private Path path;
    private Paint paint;
    private float mX,mY;
    private static final float TOLERANCE=5;
    Context context;



    public CanvasView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        this.context=context;

        path=new Path();

        paint=new Paint();

        paint.setAntiAlias(true);
        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(4f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    private void startTouch(float x,float y)
    {
        path.moveTo(x,y);
        mX=x;
        mY=y;
    }

    private void moveTouch(float x,float y)
    {
        float dx=Math.abs(x-mX);
        float dy=Math.abs(y-mY);
        if(dx>=TOLERANCE||dy>=TOLERANCE)
        {
            path.quadTo(mX,mY,(x+mX)/2,(y+mY)/2);
            mX=x;
            mY=y;
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
    }

    public void clearCanvas()
    {
        path.reset();
        invalidate();
    }

    private void upTouch()
    {
        path.lineTo(mX,mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x=event.getX();
        float y=event.getY();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                startTouch(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }

        return true;
    }



}
