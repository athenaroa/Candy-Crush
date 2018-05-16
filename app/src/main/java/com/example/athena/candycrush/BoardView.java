package com.example.athena.candycrush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback {
    Bitmap mybitmap=BitmapFactory.decodeResource(getResources(),R.drawable.image2); //Put an image called 'image' under the folder drawable
    float x;
    float y;

    public BoardView (Context context)
    {
        super(context);
        //Notify the SurfaceHolder that you'd like to receive SurfaceHolder callbacks.
        getHolder().addCallback(this);
        setFocusable(true);
        //Initialize game state variables and the game board variables

    }

    @Override
    public void surfaceCreated (SurfaceHolder holder)
    {
        //Construct game initial state
        //Create the Candy Board
        //Initialize the board with random candies

        Canvas c = holder.lockCanvas();
        this.onDraw(c);
        holder.unlockCanvasAndPost(c);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        //Called in response to changes in the surface
        //Write code here to do the necessary tasks such as adding new random candies from
        //the top of the board when the dimensions of the board change on eliminating the matched candies

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        //This is called immediately before a surface is being destroyed.
        //Write code here that needs to be executed just before the surface is destroyed
    }


    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        //Update game state in response to events:
        //touch-down, touch-up, and touch-move
        //Perform operations to check if the touch event is a valid move
        //If the move is vaid, take the necessary actions such as removing the matched candies
        //and moving the candies above the eliminated row/column to their appropriate positions.
        x = e.getX();
        y = e.getY();
        Canvas c = getHolder().lockCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        c.drawRect(x,y,x+100,y+100,paint);
        getHolder().unlockCanvasAndPost(c);
        return true;
    }

    @Override
    protected void onDraw(Canvas c)
    {
        super.onDraw(c);
        c.drawColor(Color.RED); //Set the background to red
        Rect dst=new Rect();
        dst.set(500,1500,1000,2000);
        c.drawBitmap(mybitmap, null,dst,null); //draw the image you putted in the folder drawable
    }


}
