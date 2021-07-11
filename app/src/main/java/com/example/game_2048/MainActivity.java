package com.example.game_2048;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // SCORE
    private TextView score;
    private TextView best;

    //REFRESH
    private Button refresh;

    // Layout
    private TextView tv0;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;
    private TextView tv10;
    private TextView tv11;
    private TextView tv12;
    private TextView tv13;
    private TextView tv14;
    private TextView tv15;

    // GAME OVER
    private TextView gameover;

    private ConstraintLayout constraintLayout;

    private TextView[][] board = null;

    private SwipeListener swipeListener;

    private List<TextView> blankTextView;

    private boolean over;

    private int totalScore;
    private int bestScore = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();


        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                doRefresh();
            }
        });


    }

    public void initialize() {
        // SCORE
        score = (TextView) findViewById(R.id.score);
        best = (TextView) findViewById(R.id.best);

        // Layout
        tv0 = (TextView) findViewById(R.id.tv0);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
        tv8 = (TextView) findViewById(R.id.tv8);
        tv9 = (TextView) findViewById(R.id.tv9);
        tv10 = (TextView) findViewById(R.id.tv10);
        tv11 = (TextView) findViewById(R.id.tv11);
        tv12 = (TextView) findViewById(R.id.tv12);
        tv13 = (TextView) findViewById(R.id.tv13);
        tv14 = (TextView) findViewById(R.id.tv14);
        tv15 = (TextView) findViewById(R.id.tv15);

        gameover = (TextView) findViewById(R.id.gameover);

        refresh = (Button) findViewById(R.id.refresh);

        constraintLayout = (ConstraintLayout) findViewById(R.id.relative_layout);

        board = new TextView[][]{
                {tv0, tv1, tv2, tv3},
                {tv4, tv5, tv6, tv7},
                {tv8, tv9, tv10, tv11},
                {tv12, tv13, tv14, tv15}
        };

        over = false;

        this.totalScore = 0;
        blankTextView = new ArrayList<>();

        int getInitialNumber2or4 = getInitialNumber2or4();
        int getInitialBoardIndex = getInitialBoardIndex(16);

        int[] initialTextViewIndex = getBoardIndex(getInitialBoardIndex);
        Log.d("getInitialNumber2or4", String.valueOf(getInitialNumber2or4));
        board[initialTextViewIndex[0]][initialTextViewIndex[1]].setText(String.valueOf(getInitialNumber2or4));

        swipeListener = new SwipeListener(constraintLayout);
    }

    public void doRefresh() {
        this.totalScore = 0;
        this.over = false;
        this.gameover.setText("");
        score.setText("");
        for(int i = 0;i<4;i++) {
            for(int j = 0;j < 4; j++) {
                board[i][j].setText("");
            }
        }

        initialize();
    }

    public static int getInitialNumber2or4() {
        Random random = new Random();
        int randomNumber = random.nextInt(10);

        int number = randomNumber == 4 ? 4 : 2;

        return number;
    }

    public static int getInitialBoardIndex(int length) {
        Random random = new Random();
        int randomNumber = random.nextInt(length);

        return randomNumber;
    }

    public static int[] getBoardIndex(int index) {
        int rem = index % 4;
        int div = index / 4;

        return new int[]{div, rem};
    }

    public void generateNewNumber() {

        int blankTextViewLength = getBlankTextViewSize();
        if(blankTextViewLength == 0) {
            over = true;
            gameover.setText("GAME OVER!!!");
            return;
        }
        int getInitialNumber2or4 = getInitialNumber2or4();
        int getInitialBoardIndex = getInitialBoardIndex(blankTextViewLength);

        blankTextView.get(getInitialBoardIndex).setText(String.valueOf(getInitialNumber2or4));
    }

    public boolean isGameOver() {
        return getBlankTextViewSize() == 0 ? true : false;
    }

    public int getBlankTextViewSize() {
        int length = 0;
        for(int i = 0;i<4;i++) {
            for(int j = 0;j<4;j++) {
                if(board[i][j].getText().equals("")) {
                    blankTextView.add(board[i][j]);
                    length++;
                }
            }
        }

        return length;
    }



    private class SwipeListener implements View.OnTouchListener {

        // Variable Initialization
        GestureDetector gestureDetector;

        // Constructor
        SwipeListener(View view) {
            int threshold = 100;
            int velocity_threshold = 100;

            // Swipe Swipe Gesture
            GestureDetector.SimpleOnGestureListener simpleOnGestureListener =
                    new GestureDetector.SimpleOnGestureListener(){

                        @Override
                        public boolean onDown(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                            // get xDiff
                            float xDiff = e2.getX()-e1.getX();

                            // get yDiff
                            float yDiff = e2.getY() - e1.getY();

                            try {

                                if(Math.abs(xDiff) > Math.abs(yDiff)) {


                                    // Code for Right and Left Gesture
                                    if(xDiff > 0) {
                                        swipeInitialize("RIGHT");
                                    } else {
                                        swipeInitialize("LEFT");
                                    }
                                    return true;
                                }

                                else {
                                    if(Math.abs(yDiff)>threshold && Math.abs(velocityY) > velocity_threshold) {
                                    //  Write code for up and down

                                        if(yDiff > 0) {
                                            swipeInitialize("DOWN");
                                        } else {
                                            swipeInitialize("UP");
                                        }
                                        return true;
                                    }
                                }


                            } catch (Exception e) {
                                throw e;
//                                Log.d("exception",String.valueOf(e));
                            }
                            return false;
                        }
                    };

            gestureDetector = new GestureDetector(simpleOnGestureListener);
            view.setOnTouchListener(this);
        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        public void swipeLeft () {

            String[][] tempBoard = new String[4][4];
            for(int i = 0;i < 4; i++) {
                for(int j = 0;j < 4; j++) {
                    tempBoard[i][j] = (String) board[i][j].getText();
                }
            }

            boolean swapped = swipeLURD(tempBoard);
            if(!swapped) return;
            for(int i = 0;i < 4; i++) {
                for(int j = 0;j < 4; j++) {
                    board[i][j].setText(tempBoard[i][j]);
                }
            }



            swipe();
        }

        public void swipeRight () {

            String[][] tempBoard = new String[4][4];
            int _i = 0;
            int _j = 0;
            for(int i = 0;i < 4; i++) {
                _j = 0;
                for(int j = 3;j >= 0; j--) {
                    Log.d("_i _j", _i+"" +_j);
                    tempBoard[_i][_j] = (String) board[i][j].getText();
                    _j++;
                }
                _i++;
            }

            _i = 0;
            _j = 0;
            boolean swapped = swipeLURD(tempBoard);
            if(!swapped) return;

            for(int i = 0;i < 4; i++) {
                _j = 0;
                for(int j = 3;j >= 0; j--) {
                    board[i][j].setText(tempBoard[_i][_j]);
                    _j++;
                }
                _i++;
            }


            swipe();

        }

        public void swipeUp () {

            String[][] tempBoard = new String[4][4];
            for(int i = 0;i < 4; i++) {
                for(int j = 0;j < 4; j++) {
                    tempBoard[i][j] = (String) board[j][i].getText();
                }
            }

            boolean swapped = swipeLURD(tempBoard);
            if(!swapped) return;

            for(int i = 0;i < 4; i++) {
                for(int j = 0;j < 4; j++) {
                    board[j][i].setText(tempBoard[i][j]);
                }
            }

            swipe();
        }


        public void swipeDown () {
            String[][] tempBoard = new String[4][4];
            int _i = 0;
            int _j = 0;
            for(int i = 0;i < 4; i++) {
                _j = 0;
                for(int j = 3;j >= 0; j--) {
                    Log.d("_i _j", _i+"" +_j);
                    tempBoard[_i][_j] = (String) board[j][i].getText();
                    _j++;
                }
                _i++;
            }

            _i = 0;
            _j = 0;
            boolean swapped = swipeLURD(tempBoard);
            if(!swapped) return;
            for(int i = 0;i < 4; i++) {
                _j = 0;
                for(int j = 3;j >= 0; j--) {
                    board[j][i].setText(tempBoard[_i][_j]);
                    _j++;
                }
                _i++;
            }

            swipe();
        }


        public void swipe () {
            blankTextView.clear();
            generateNewNumber();
            if (totalScore > bestScore) {bestScore = totalScore;
            best.setText(String.valueOf(bestScore));
            }
        }



        public boolean swipeLURD(String[][] tempBoard) {
            boolean swapped = false;
            int i = 0 ;
            while(i < 4) {
                for(int j = 0;j<4;j++) {
                    if (tempBoard[i][j].equals("")) {
                        for(int k = j+1;k<4;k++) {
                            if(!tempBoard[i][k].equals("")){
                                tempBoard[i][j] =tempBoard[i][k];
                                tempBoard[i][k] = "";
                                swapped = true;
                                break;
                            }
                        }
                    }
                }
                i++;
            }
            i = 0;


            while(i<4) {
                Log.d("yes","Aa rha hai");
                for(int j=0;j<3;j++) {
                    String text1 = tempBoard[i][j];
                    String text2 = tempBoard[i][j+1];
                    if(!text1.equals("") && text1.equals(text2)) {
                        swapped = true;
                        Log.d(i+" "+j,"Aa rha hai");
                        Log.d(i + " " + j, String.valueOf(tempBoard[i][j]));
                        int total = Integer.parseInt(text1)+Integer.parseInt(text2);
                        totalScore = totalScore + total;
                        String totalScoreToSet = String.valueOf(totalScore);
                        score.setText(totalScoreToSet);
                        tempBoard[i][j] = String.valueOf(total);
                        for(int k = j+2;k<4;k++) {
                            tempBoard[i][k-1] = tempBoard[i][k];
                        }
                        tempBoard[i][3] = "";
                    }
                }
                i++;
            }
            if(!swapped) {
                if (isGameOver()) {
                    over = true;
                    gameover.setText("GAME OVER!!!");
                }
            }
            return swapped;
        }

        public void swipeInitialize(String operation) {
            if(over) return;
            switch (operation) {
                case "LEFT":
                    swipeLeft();
                    break;
                case "RIGHT":
                    swipeRight();
                    break;
                case "UP":
                    swipeUp();
                    break;
                case "DOWN":
                    swipeDown();
                    break;
                default:
                    Log.d("Wrong operation",operation);
            }
        }

        public int getFontSize(int length) {
            int MAX_FONT_SIZE = 38;
            int MULTIPLIER = 2;
            return MAX_FONT_SIZE-(MULTIPLIER*length);
        }
    }
}
