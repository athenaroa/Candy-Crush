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

import java.util.Random;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback {
    Bitmap redCandy =BitmapFactory.decodeResource(getResources(),R.drawable.redcandy); //Put an image called 'image' under the folder drawable
    Bitmap greenCandy =BitmapFactory.decodeResource(getResources(),R.drawable.greencandy);
    Bitmap blueCandy =BitmapFactory.decodeResource(getResources(),R.drawable.bluecandy);

    Bitmap redJelly  =BitmapFactory.decodeResource(getResources(),R.drawable.redjelly);
    Bitmap greenJelly  =BitmapFactory.decodeResource(getResources(),R.drawable.greenjelly);
    Bitmap blueJelly  =BitmapFactory.decodeResource(getResources(),R.drawable.bluejelly);
    Bitmap pinkJelly  =BitmapFactory.decodeResource(getResources(),R.drawable.pinkjelly);

    Bitmap candyCorn  =BitmapFactory.decodeResource(getResources(),R.drawable.candycorn);
    Bitmap candyCornOpen  =BitmapFactory.decodeResource(getResources(),R.drawable.candycornopen);




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
        //If the move is valid, take the necessary actions such as removing the matched candies
        //and moving the candies above the eliminated row/column to their appropriate positions.
        x = e.getX();
        y = e.getY();


        switch(e.getAction())
        {
            case (MotionEvent.ACTION_MOVE) :
                Canvas c = getHolder().lockCanvas();
                Paint paint = new Paint();
                paint.setColor(Color.BLUE);
                c.drawRect(x,y,x+100,y+100,paint);
                getHolder().unlockCanvasAndPost(c);
                return true;
            case (MotionEvent.ACTION_UP):
                Canvas d = getHolder().lockCanvas();
                Paint paint2 = new Paint();
                paint2.setColor(Color.BLACK);
                d.drawRect(x,y,x+100,y+100,paint2);
                getHolder().unlockCanvasAndPost(d);
                return true;
             case (MotionEvent.ACTION_DOWN):
                 Canvas f = getHolder().lockCanvas();
                 Paint paint3 = new Paint();
                 paint3.setColor(Color.GREEN);
                 f.drawRect(x,y,x+100,y+100,paint3);
                 getHolder().unlockCanvasAndPost(f);
                 return true;
            default:
                return super.onTouchEvent(e);


        }

    }

    @Override
    protected void onDraw(Canvas c)
    {
        super.onDraw(c);
        c.drawColor(Color.YELLOW); //Set the background to yellow

        for(int i = 100; i < 1000; i += 100) //Width
        {
            for(int j = 500; j < 1400; j += 100) //Height
            {
                Rect dst=new Rect();
                dst.set(i,j ,i + 90,j + 90);

                Random random = new Random();
                int candy = random.nextInt(9);
                switch(candy)
                {
                    case(0):
                        c.drawBitmap(redCandy, null,dst,null);
                        break;
                    case(1):
                        c.drawBitmap(blueCandy, null,dst,null);
                        break;
                    case(2):
                        c.drawBitmap(greenCandy, null,dst,null);
                        break;
                    case(3):
                        c.drawBitmap(redJelly, null,dst,null);
                        break;
                    case(4):
                        c.drawBitmap(blueJelly, null,dst,null);
                        break;
                    case(5):
                        c.drawBitmap(greenJelly, null,dst,null);
                        break;
                    case(6):
                        c.drawBitmap(pinkJelly, null,dst,null);
                        break;
                    case(7):
                        c.drawBitmap(candyCorn, null,dst,null);
                    case(8):
                        c.drawBitmap(candyCornOpen, null,dst,null);
                }

            }
        }
        
    }


}
