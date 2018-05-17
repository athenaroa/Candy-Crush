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

    float x;
    float y;
    int score = 0;
    private Bitmap[] candy;
    private int[][] board;
    private int numRow = 9;
    private int numColumn = 9;
    Bitmap redCandy;
    Bitmap greenCandy;
    Bitmap blueCandy;
    Bitmap redJelly;
    Bitmap greenJelly;
    Bitmap blueJelly;
    Bitmap pinkJelly;
    Bitmap candyCorn;
    Bitmap candyCornOpen;


    public BoardView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        //Initialize game state variables and the game board variables

        board = new int[9][9];


        redCandy = BitmapFactory.decodeResource(getResources(), R.drawable.redcandy); //Put an image called 'image' under the folder drawable
        greenCandy = BitmapFactory.decodeResource(getResources(), R.drawable.greencandy);
        blueCandy = BitmapFactory.decodeResource(getResources(), R.drawable.bluecandy);

        redJelly = BitmapFactory.decodeResource(getResources(), R.drawable.redjelly);
        greenJelly = BitmapFactory.decodeResource(getResources(), R.drawable.greenjelly);
        blueJelly = BitmapFactory.decodeResource(getResources(), R.drawable.bluejelly);
        pinkJelly = BitmapFactory.decodeResource(getResources(), R.drawable.pinkjelly);

        candyCorn = BitmapFactory.decodeResource(getResources(), R.drawable.candycorn);
        candyCornOpen = BitmapFactory.decodeResource(getResources(), R.drawable.candycornopen);

        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numColumn; j++) {
                Random random = new Random();
                int c = random.nextInt(9);
                board[i][j] = c;
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Construct game initial state
        //Create the Candy Board
        //Initialize the board with random candies

        Canvas c = holder.lockCanvas();
        this.onDraw(c);
        holder.unlockCanvasAndPost(c);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Called in response to changes in the surface
        //Write code here to do the necessary tasks such as adding new random candies from
        //the top of the board when the dimensions of the board change on eliminating the matched candies

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //This is called immediately before a surface is being destroyed.
        //Write code here that needs to be executed just before the surface is destroyed
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //Update game state in response to events:
        //touch-down, touch-up, and touch-move
        //Perform operations to check if the touch event is a valid move
        //If the move is valid, take the necessary actions such as removing the matched candies
        //and moving the candies above the eliminated row/column to their appropriate positions.
        x = e.getX();
        y = e.getY();
        

        switch (e.getAction()) {
            case (MotionEvent.ACTION_MOVE):
                /*
                Canvas c = getHolder().lockCanvas();
                Paint paint = new Paint();
                paint.setColor(Color.BLUE);
                c.drawRect(x,y,x+20,y+20,paint);
                getHolder().unlockCanvasAndPost(c);
                */
                return true;

            case (MotionEvent.ACTION_UP):

                /*
                Canvas d = getHolder().lockCanvas();
                Paint paint2 = new Paint();
                paint2.setColor(Color.BLACK);
                d.drawRect(x,y,x+20,y+20,paint2);
                getHolder().unlockCanvasAndPost(d);
                */


                return true;
            case (MotionEvent.ACTION_DOWN):
                /*
                 Canvas f = getHolder().lockCanvas();
                 Paint paint3 = new Paint();
                 paint3.setColor(Color.GREEN);
                 f.drawRect(x,y,x+20,y+20,paint3);
                 getHolder().unlockCanvasAndPost(f);
                */


                return true;
            default:
                return super.onTouchEvent(e);
        }

    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        c.drawColor(Color.YELLOW); //Set the background to yellow

        Paint s = new Paint();
        s.setColor(Color.BLACK);
        s.setTextSize(80);
        c.drawText("Score = " + score, 100, 300, s);
        Rect dst = new Rect();

        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numColumn; j++) {
                int index = board[i][j];

                dst.set(j * 120, (i * 120) + 450, (j + 1) * 120, ((i + 1) * 120) + 450);
                switch (index) {
                    case (0):
                        c.drawBitmap(redCandy, null, dst, null);
                        break;
                    case (1):
                        c.drawBitmap(blueCandy, null, dst, null);

                        break;
                    case (2):
                        c.drawBitmap(greenCandy, null, dst, null);

                        break;
                    case (3):
                        c.drawBitmap(redJelly, null, dst, null);

                        break;
                    case (4):
                        c.drawBitmap(blueJelly, null, dst, null);
                        break;
                    case (5):
                        c.drawBitmap(greenJelly, null, dst, null);

                        break;
                    case (6):
                        c.drawBitmap(pinkJelly, null, dst, null);

                        break;
                    case (7):
                        c.drawBitmap(candyCorn, null, dst, null);

                        break;
                    case (8):
                        c.drawBitmap(candyCornOpen, null, dst, null);

                        break;
                }

            }
        }

    }
}