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
import java.lang.*;

import java.util.Random;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback {

    float x;
    float y;
    int score = 0;
    private Bitmap[] candy;
    private int[][] board;
    private int numRow = 9;
    private int numColumn = 9;
    final int candyWidth = 120;
    int xStart, yStart, xEnd, yEnd;


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
        xStart = 0;
        yStart = 0;
        xEnd = 0;
        yEnd = 0;

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
                System.out.println(c);
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

    public int getIndex(float pos)
    {
        int index;

        if(pos < 120) {
            index = 0;
        }
        else if((pos > 120) && (pos < 240))
        {
            index = 1;
        }
        else if((pos > 240) && (pos < 360))
        {
            index = 2;
        }
        else if((pos > 360) && (pos < 480))
        {
            index =  3;
        }
        else if((pos > 480) && (pos < 600))
        {
            index = 4;
        }
        else if((pos > 600) && (pos < 720))
        {
            index =  5;
        }
        else if((pos > 720) && (pos < 840))
        {
            index =  6;
        }
        else if((pos > 840) && (pos < 960))
        {
            index = 7;
        }
        else if((pos > 960) && (pos < 1080))
        {
            index = 8;
        }
        else
        {
            System.out.println("Not in valid range");
            index = 100;
        }
        return index;
    }

    public void switchCandies(int xStart, int yStart, int xEnd, int yEnd)
    {


        int candy1, candy2, temp;

        candy1 = board[yStart][xStart];
        System.out.println(" initial Candy1 = " + candy1);
        candy2 = board[xEnd][yEnd];
        System.out.println(" initial Candy2 = " + candy2);

        temp = candy1;
        candy1 = candy2;
        candy2 = temp;


        board[yStart][xStart] = candy1;
        System.out.println(" final Candy1 = " + board[yStart][xStart]);
        board[yEnd][xEnd] = candy2;
        System.out.println(" final Candy2 = " + board[yEnd][xEnd]);


        System.out.println("Candies were switched");
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

                //return true;
                break;
            case (MotionEvent.ACTION_UP):
                xEnd = getIndex(x);
                yEnd = getIndex(y - 450);

                //return true;
                break;

            case (MotionEvent.ACTION_DOWN):
                xStart = getIndex(x);
                yStart = getIndex(y - 450);

                System.out.println("xStart =" + xStart);
                System.out.println("yStart =" + yStart);

                System.out.println("Candy" + board[yStart][xStart]);

                //return true;
                break;
            default:
                return super.onTouchEvent(e);
        }

        if((xStart != xEnd) && (yStart != yEnd))
        {
            switchCandies(xStart,yStart,xEnd,yEnd);

            surfaceCreated(getHolder());
        }


        return true;
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

                dst.set(j * candyWidth, (i * candyWidth) + 450, (j + 1) * candyWidth, ((i + 1) * candyWidth) + 450);
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