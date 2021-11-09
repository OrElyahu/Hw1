package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView[][] imageMat;
    private ImageView[] imageLives;
    private ImageView[] imagePlayer;
    private ImageButton[] buttonsArr;

    private int[][] indexMat;
    private final int MAX_LIVES = 3;
    private int currentLives;
    private final int SECOND = 1000;
    private Handler handler;
    private int playerPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        buttonsListener();
        runLogic();
    }

    @Override
    protected void onStart() {
        super.onStart();
        StopTick();
        runLogic();
    }

    @Override
    protected void onStop() {
        super.onStop();
        StopTick();
    }



    private void runLogic() {
        if(currentLives > 0){
            updateUI();
            Tick();
        }

    }

    private void findViews() {
        imageMat = new ImageView[][]{
                {findViewById(R.id.image00), findViewById(R.id.image01), findViewById(R.id.image02)},
                {findViewById(R.id.image10), findViewById(R.id.image11), findViewById(R.id.image12)},
                {findViewById(R.id.image20), findViewById(R.id.image21), findViewById(R.id.image22)},
        };

        imagePlayer = new ImageView[]{
                findViewById(R.id.image30), findViewById(R.id.image31), findViewById(R.id.image32)
        };

        imageLives = new ImageView[]{
                findViewById(R.id.heart1),
                findViewById(R.id.heart2),
                findViewById(R.id.heart3)
        };

        buttonsArr = new ImageButton[]{
                findViewById(R.id.leftButton),
                findViewById(R.id.rightButton)
        };

        indexMat = new int[imageMat.length + 1][imageMat[0].length];

        playerPos = 1;
        currentLives = MAX_LIVES;
    }

    private void addObstacle(){
        Random random = new Random();
        int val = random.nextInt(indexMat[0].length);
        indexMat[0][val] = 1;
    }

    private void shiftDown(){
        for(int i = 0; i < indexMat.length; ++i){
            for(int j = 0;j < indexMat[i].length ; ++j){
                if(indexMat[i][j] == 1){
                    indexMat[i++][j] = 0;
                    if(i < indexMat.length)
                        indexMat[i][j] = 1;
                    break;
                }
            }
        }

    }

    private void updateUI() {

        for(int i = 0; i < indexMat.length; ++i){
            for(int j = 0; j < indexMat[i].length; ++j) {
                if(indexMat[i][j] == 1){
                    if(i < imageMat.length){    //case image mat
                        imageMat[i][j].setVisibility(View.VISIBLE);
                    }else{  //case image player
                        if(checkCrash(j))
                            updateLives();
                    }

                    if(i > 0)
                        imageMat[i-1][j].setVisibility(View.INVISIBLE);
                    break;
                }
            }
        }




    }


    private boolean checkCrash(int pos){
        return pos == playerPos;
    }

    private void buttonsListener(){
        for(int i = 0; i < buttonsArr.length; ++i){
            final int buttonIndex = i;
            buttonsArr[i].setOnClickListener(v -> move(buttonIndex));
        }
    }

    private void move(int buttonIndex){
        if(buttonIndex == 0){ //case left
            if(playerPos > 0){
                imagePlayer[playerPos--].setVisibility(View.INVISIBLE);
                imagePlayer[playerPos].setVisibility(View.VISIBLE);
            }
        }else{ //case right
            if(playerPos < 2){
                imagePlayer[playerPos++].setVisibility(View.INVISIBLE);
                imagePlayer[playerPos].setVisibility(View.VISIBLE);
            }
        }
    }

    private void Tick() {
        handler = new Handler();
        handler.postDelayed(() -> {
            //Here are delayed functions
            shiftDown();
            if(availObstacle())
                addObstacle();

            runLogic();
        }, SECOND);
    }

    private boolean availObstacle(){
        for(int i = 0; i < 2; ++i){
            for(int j = 0; j < indexMat[i].length; ++j){
                if(indexMat[i][j] == 1)
                    return false;
            }
        }
        return true;
    }

    private void StopTick(){
        handler.removeCallbacksAndMessages(null);
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    private void updateLives(){
        imageLives[--currentLives].setVisibility(View.INVISIBLE);
        vibrate();

        if(currentLives == 0){
            StopTick();
            Toast.makeText(getApplicationContext(),"Game Over", Toast.LENGTH_LONG).show();
            exitGame();
        }

    }

    private void exitGame() {
        cleanScreen();
        stopButtonListener();
    }

    private void stopButtonListener() {
        for (ImageButton imageButton : buttonsArr) {
            imageButton.setOnClickListener(null);
        }
    }

    private void cleanScreen() {
        for(int i = 0; i < imageMat.length; ++i){
            for(int j = 0; j < indexMat[i].length; ++j)
                imageMat[i][j].setVisibility(View.INVISIBLE);
        }
    }


}

