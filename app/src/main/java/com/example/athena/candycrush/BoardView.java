package com.example.athena.candycrush;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.lang.*;

import java.util.Random;

import static android.view.MotionEvent.INVALID_POINTER_ID;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback {


    int score = 0;
    private int[][] remove;
    private int[][] board;
    private int numRow = 9;
    private int numColumn = 9;
    final int candyWidth = 120;
    int xStart, yStart, xEnd, yEnd;
    int end = 0;
    int maxScore = 150;


    private int mActivePointerId = INVALID_POINTER_ID;
    private float mLastTouchX;
    private float mLastTouchY;
    public float mPosX;
    public float mPosY;



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
        remove = new int[9][9];
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
                //System.out.println(c);
            }
        }

        while((existingMatch()))
        {
            removeCandy(remove);
            fillBoard(remove);
        }

        for(int i = 0; i < numRow; i++ )
        {
            for(int j = 0; j < numColumn; j++)
            {
                remove[i][j] = 0;
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
        //System.out.println(" initial Candy1 = " + candy1);
        candy2 = board[yEnd][xEnd];
        //System.out.println(" initial Candy2 = " + candy2);

        temp = candy1;
        candy1 = candy2;
        candy2 = temp;


        board[yStart][xStart] = candy1;
        //System.out.println(" final Candy1 = " + board[yStart][xStart]);
        board[yEnd][xEnd] = candy2;
        //System.out.println(" final Candy2 = " + board[yEnd][xEnd]);


        //System.out.println("Candies were switched");
    }

    public boolean existingMatch()
    {
        System.out.println("Went into existing match");
        boolean match = false;

        for(int i = 0; i < numRow; i++ )
        {
            for(int j = 0; j < numColumn; j++)
            {
                if(middleHorizontalMatch(i,j,i,j) || middleVeritcalMatch(i,j,i,j) || edgeMatch(i,j,i,j))
                {
                    match = true;
                }
            }
        }
        return match;
    }


    public boolean endGame()
    {
        boolean r = true;


        if(score > maxScore)
        {
            end = 2;
        }
        else { }
            /*
            for (int i = 0; i < numRow; i++) {
                for (int j = 0; j < numColumn; j++) {
                    if ((i + 1) < numRow) {
                        if (vaildMove(i, j, i + 1, j)) {
                            r = false;
                        }
                    }

                    if ((i - 1) >= 0) {
                        if (vaildMove(i, j, i - 1, j)) {
                            r = false;
                        }
                    }

                    if ((j - 1) >= 0) {
                        if (vaildMove(i, j, i, j - 1)) {
                            r = false;
                        }
                    }

                    if ((j + 1) < numColumn) {
                        if (vaildMove(i, j, i, j + 1)) {
                            r = false;
                        }
                    }
                }
            }

        }
        if( (score < 300) && (r != false))
        {
            end = 1;
        }

        */
        return r;
    }


    public boolean middleHorizontalMatch(int startPosX, int startPosY, int endPosX, int endPosY)
    {
        boolean match = false;
        int rightCount = 0;
        int leftCount = 0;

        int candyMatch = board[startPosY][startPosX];
        switchCandies(startPosX,startPosY,endPosX,endPosY);

        while((endPosX + (rightCount + 1)) < numColumn)
        {
            if(board[endPosY][endPosX + (rightCount + 1)] == candyMatch)
            {
                //System.out.println("Right Matching Candy");
                rightCount++;
                //System.out.println("rightCount = " + rightCount);
            }
            else
            {
                break;
            }
        }
        while((endPosX - (leftCount + 1)) >= 0)
        {
            if(board[endPosY][endPosX - (leftCount + 1)] == candyMatch)
            {
                //System.out.println("Left Matching Candy");
                leftCount++;
                //System.out.println("leftCount = " + leftCount);
            }
            else
            {
                break;
            }
        }

        if((rightCount >= 1) && (leftCount >= 1))
        {
            remove[endPosY][endPosX] = 1;

            for(int i = 0; i <= rightCount; i++)
            {
                remove[endPosY][endPosX + rightCount] = 1;
            }
            for(int j = 0; j <= leftCount; j++)
            {
                remove[endPosY][endPosX - leftCount] = 1;
            }
            match = true;
        }


        if(match == true)
        {
            System.out.println("Return true middleHorizontalMatch");
        }
        else
        {
            System.out.println("Return false middleHorizontalMatch");
        }
        switchCandies(endPosX,endPosY,startPosX,startPosY);
        return match;
    }

    public boolean middleVeritcalMatch(int startPosX, int startPosY, int endPosX, int endPosY)
    {
        boolean match = false;
        int upCount = 0;
        int downCount = 0;

        int candyMatch = board[startPosY][startPosX];
        switchCandies(startPosX,startPosY,endPosX,endPosY);
        while((endPosY + (downCount + 1)) < numColumn)
        {
            if(board[endPosY + (downCount + 1)][endPosX] == candyMatch)
            {
                //System.out.println("Down Matching Candy");
                downCount++;
                //System.out.println("downCount = " + downCount);
            }
            else
            {
                break;
            }
        }
        while((endPosY - (upCount + 1)) >= 0)
        {
            if(board[endPosY - (upCount + 1)][endPosX] == candyMatch)
            {
                //System.out.println("Up Matching Candy");
                upCount++;
                //System.out.println("upCount = " + upCount);
            }
            else
            {
                break;
            }
        }

        if((upCount >= 1) && (downCount >= 1))
        {
            remove[endPosY][endPosX] = 1;

            for(int i = 0; i <= upCount; i++)
            {
                remove[endPosY - upCount][endPosX] = 1;
            }
            for(int j = 0; j <= downCount; j++)
            {
                remove[endPosY + downCount][endPosX] = 1;
            }
            match = true;
        }



        /*
        if((endPosY + 1 < numRow) && (endPosY - 1 >= 0))
        {
            if((board[endPosY + 1][endPosX] == candyMatch) && (board[endPosY - 1][endPosX] == candyMatch))
            {
                match = true;
                remove[endPosY][endPosX] = 1;
                remove[endPosY + 1][endPosX] = 1;
                remove[endPosY - 1][endPosX] = 1;
            }
        }

        */

        if(match == true)
        {
            System.out.println("Return true middleVerticalMatch");
        }
        else
        {
            System.out.println("Return false middleVerticalMatch");
        }
        switchCandies(endPosX,endPosY,startPosX,startPosY);
        return match;
    }


    public boolean edgeMatch(int startPosX, int startPosY, int endPosX, int endPosY)
    {
        boolean match = false;
        int candyMatch = board[startPosY][startPosX];
        switchCandies(startPosX,startPosY,endPosX,endPosY);
        int count = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;

        while((endPosX + (count + 1)) < numColumn)
        {
            if(board[endPosY][endPosX + (count+1)] == candyMatch)
            {
                System.out.println("Left Horizontal Match");
                count++;
            }
            else
            {
                break;
            }
        }

        while((endPosX - (count2 + 1)) >= 0)
        {
            if(board[endPosY][endPosX - (count2 + 1)] == candyMatch)
            {
                System.out.println("Right Horizontal Match");
                count2++;
            }
            else
            {
                break;
            }
        }

        while((endPosY + (count3 + 1)) < numRow)
        {
            //System.out.println("Within <9  vertical boundaries");
            if(board[endPosY + (count3 + 1)][endPosX] == candyMatch)
            {
                System.out.println("Top Vertical Match");
                count3++;
            }
            else
            {
                break;
            }
        }

        while((endPosY - (count4 + 1)) >= 0)
        {
            //System.out.println("Within > 0  vertical boundaries");
            if(board[endPosY - (count4 +1)][endPosX] == candyMatch)
            {
                System.out.println("Bottom Vertical Match");
                count4++;
            }
            else
            {
                break;
            }
        }

        if(count >= 2)
        {
            remove[endPosY][endPosX] = 1;
            for (int i = 0; i <= count; i++)
            {
                remove[endPosY][endPosX + i] = 1;
                System.out.println("Left Vertical Match -- candy removed. i = " + i);
            }
            match = true;
        }
        if(count2 >= 2)
        {
            remove[endPosY][endPosX] = 1;
            for (int i = 0; i <= count2; i++)
            {
                remove[endPosY][endPosX - i] = 1;
                System.out.println("Right Vertical Match -- candy removed. i = " + i);
            }
            match = true;
        }
        if(count3 >= 2)
        {
            remove[endPosY][endPosX] = 1;
            for(int i = 0; i <= count3; i++)
            {
                remove[endPosY + i][endPosX] = 1;
                System.out.println("Top Vertical Match -- candy removed. i = " + i);
            }
            match = true;
        }
        if(count4 >= 2)
        {
            remove[endPosY][endPosX] = 1;
            for(int i = 0; i <= count4; i++)
            {
                remove[endPosY - i][endPosX] = 1;
                System.out.println("Bottom Vertical Match -- candy removed. i = " + i);
            }
            match = true;
        }

        switchCandies(endPosX,endPosY,startPosX,startPosY);
        return match;
    }

    public boolean vaildMove(int xS, int yS, int xE, int yE)
    {
        boolean valid = false; //Returns true if valid move
        int changeX = Math.abs(xS - xE);
        int changeY = Math.abs(yS - yE);
        int candy1 = board[yS][xS];
        int candy2 = board[yE][xE];

        if( ((xS != xE) && (yS == yE)) || ((xS == xE) && (yS != yE))) //True if start and end is not the same box
        {
            //System.out.println("First conditional True");
            if(candy1 != candy2) //True if candies at the start and end are not the same
            {
                //System.out.println("Second conditional True");
                if( (changeX == 1) || (changeY == 1)) //True if candies are only one index apart from each other
                {
                    //System.out.println("Third conditional True");
                    if(middleHorizontalMatch(xS,yS,xE,yE) || middleVeritcalMatch(xS,yS,xE,yE) || edgeMatch(xS,yS,xE,yE))
                    {
                        //System.out.println("fourth conditional True, switch should occur with candy S to E");
                        valid = true;
                    }
                    if(middleHorizontalMatch(xE,yE,xS,yS) || middleVeritcalMatch(xE,yE,xS,yS) || edgeMatch(xE,yE,xS,yS))
                    {
                        //System.out.println("fourth conditional True, switch should occur with candy E to S");
                        valid = true;
                    }

                }
            }
        }
        if(valid == false)
        {
            System.out.println("No switch occured");
        }
        return valid;
    }

    public void removeCandy(int[][]r)
    {
        for(int i = 0; i < numRow; i++ )
        {
            for(int j = 0; j < numColumn; j++)
            {
                if(r[i][j] == 1)
                {
                    board[i][j] = 9;
                    score ++;
                    System.out.println("Score = " + score);
                }

            }
        }
    }


    public void fillBoard(int[][]r)
    {
        System.out.println("Goes into fillBoard Function");
        for (int i = 8; i >= 0; i--) {
            for (int j = 8; j >= 0; j--) {


                Random random = new Random();
                int c = random.nextInt(9);
                int candyDrop;
                int repeat = 1;

                if( board[0][j] == 9) //if candy is at the top and removed
                {
                   System.out.println("Candy is on top");
                   r[i][j] = 0;
                   board[i][j] = c;
                }
                else if (board[i][j] == 9) {
                    while(board[i][j] == 9)
                    {
                        candyDrop = board[i - repeat][j];
                        if(candyDrop == 9)
                        {
                            repeat++;
                        }
                        else
                        {
                            board[i][j] = candyDrop;
                            board[i - repeat][j] = 9;
                            r[i][j] = 0;
                        }

                    }
                }

            }
        }



    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //Update game state in response to events:
        //touch-down, touch-up, and touch-move
        //Perform operations to check if the touch event is a valid move
        //If the move is valid, take the necessary actions such as removing the matched candies
        //and moving the candies above the eliminated row/column to their appropriate positions.
        surfaceCreated(getHolder());

        final int action = MotionEventCompat.getActionMasked(e);

        int move = 0; //L to R  = 1 , R to L = -1, U to D = 2, D to U = -2

        switch (action) {
            case (MotionEvent.ACTION_MOVE):
            {
                //Find the index of the active pointer and fetch its position
                final int pointerIndex = MotionEventCompat.findPointerIndex(e, mActivePointerId);
                //System.out.println("ACTION_MOVE");
                //System.out.println("pointerIndex = " + pointerIndex);

                final float x = MotionEventCompat.getX(e, pointerIndex);
                final float y = MotionEventCompat.getY(e, pointerIndex);
                //System.out.println("x = " + x + "\t" + "y = " + y);

                //Calculate the distance moved
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;

                //System.out.println("dx = " + dx + "\t" + "dy = " + dy);

                mPosX += dx;
                mPosY += dy;
                //System.out.println("mPosX = " + mPosX + "\t" + "mPosY = " + mPosY);


                invalidate();

                //Remember this touch position for the next move event
                mLastTouchX = x;
                mLastTouchY = y;


                if(dx > 0) {move = 1; }
                else if ( dx < 0) { move = -1;}
                else if (dy > 0) { move = 2;}
                else if (dy < 0) {move = -2;}
                else {}

                if(move == 1) {System.out.println("L to R");}
                else if (move == -1) { System.out.println("R to L");}
                else if (move == 2) { System.out.println("U to D");}
                else if (move == -2){System.out.println("D to U");}
                else {System.out.println("Error occurred");}

                break;
            }
            case (MotionEvent.ACTION_UP):
            {
                final int pointIndex = MotionEventCompat.getActionIndex(e);
                final float x = MotionEventCompat.getX(e, pointIndex);
                final float y = MotionEventCompat.getY(e, pointIndex);

                System.out.println("ACTION_UP");
                //System.out.println("x = " + x + "\t" + "y = " + y);

                //Remember where we started (for dragging)
                mLastTouchX = x;
                mLastTouchY = y;
                //Save the ID of this pointer (for dragging)
                mActivePointerId = MotionEventCompat.getPointerId(e, 0);

                //System.out.println("mActivePointId = " + mActivePointerId);



                xEnd = getIndex(mLastTouchX);
                yEnd = getIndex(mLastTouchY - 450);

                //System.out.println("xEnd =" + xEnd);
                //System.out.println("yEnd =" + yEnd);

                System.out.println("Candy" + board[yEnd][xEnd]);


                System.out.println("Determining if candies will switch");
                System.out.println("xStart =" + xStart);
                System.out.println("yStart =" + yStart);
                System.out.println("xEnd =" + xEnd);
                System.out.println("yEnd =" + yEnd);


                if(vaildMove(xStart,yStart,xEnd,yEnd))
                {
                    switchCandies(xStart,yStart,xEnd,yEnd);
                    surfaceCreated(getHolder());
                    removeCandy(remove);
                    surfaceCreated(getHolder());
                    fillBoard(remove);
                    surfaceCreated(getHolder());

                }

                if(existingMatch())
                {
                    removeCandy(remove);
                    surfaceCreated(getHolder());
                    fillBoard(remove);
                    surfaceCreated(getHolder());
                }

                break;
            }
            case (MotionEvent.ACTION_CANCEL):
            {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case (MotionEvent.ACTION_POINTER_UP):
            {
                //System.out.println("ACTION_POINTER_UP");
                final int pointerIndex = MotionEventCompat.getActionIndex(e);
                final int pointerId = MotionEventCompat.getPointerId(e,pointerIndex);

                if(pointerId == mActivePointerId)
                {
                    //This was our active pointer going up. Choose a new
                    //active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1: 0;
                    mLastTouchX = MotionEventCompat.getX(e, newPointerIndex);
                    mLastTouchY = MotionEventCompat.getY(e,newPointerIndex);
                    mActivePointerId = MotionEventCompat.getPointerId(e,newPointerIndex);
                }
            }


            case (MotionEvent.ACTION_DOWN):
             {
                final int pointIndex = MotionEventCompat.getActionIndex(e);
                final float x = MotionEventCompat.getX(e, pointIndex);
                final float y = MotionEventCompat.getY(e, pointIndex);

                System.out.println("ACTION_DOWN");
               // System.out.println("x = " + x + "\t" + "y = " + y);

                //Remember where we started (for dragging)
                mLastTouchX = x;
                mLastTouchY = y;
                //Save the ID of this pointer (for dragging)
                mActivePointerId = MotionEventCompat.getPointerId(e, 0);

                //System.out.println("mActivePointId = " + mActivePointerId);



                xStart = getIndex(mLastTouchX);
                yStart = getIndex(mLastTouchY - 450);

                //System.out.println("xStart =" + xStart);
                //System.out.println("yStart =" + yStart);

                //System.out.println("Candy" + board[yStart][xStart]);

                break;
            }
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
                    case(9):
                        Paint paint = new Paint();
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.YELLOW);
                        c.drawRect(dst, paint);
                        break;
                }

            }
        }

        if(existingMatch())
        {
            removeCandy(remove);
            fillBoard(remove);
        }

        if(endGame())
        {

            if(end == 1) //no more moves left
            {
                //c.drawColor(Color.BLACK);
                s.setColor(Color.BLACK);
                s.setTextSize(100);
                c.drawText("NO MORE MOVES", 150, 700, s);

                Paint s2 = new Paint();
                s2.setColor(Color.WHITE);
                s2.setTextSize(70);
                //c.drawText("Click anywhere for New Game", 100, 900, s2);
            }
            else if(end == 2) //User reached the score goal
            {
                c.drawColor(Color.BLACK);
                s.setColor(Color.WHITE);
                s.setTextSize(65);
                c.drawText("YOU REACHED " + score + " POINTS!", 150, 700, s);

                Paint s2 = new Paint();
                s2.setColor(Color.WHITE);
                s2.setTextSize(70);
                //c.drawText("Click anywhere for New Game", 100, 900, s2);
            }
        }


    }
}