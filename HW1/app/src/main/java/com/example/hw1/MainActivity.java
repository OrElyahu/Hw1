package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageView[][] imageMat;
    private ImageView[] lives;
    private ImageView[] imagePlayer;
    private int[][] indexMat;
    private ImageButton[] buttonsArr;
    private final int MAX_LIVES = 3;
    private int currentLives;
    private final int SECOND = 1000;
    private Timer timer;
    private int playerPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentLives = MAX_LIVES;
        findViews();
        updateUI();
        runLogic();

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
        runLogic();
    }

    @Override
    protected void onStop() {
        super.onStop();
        StopTick();
    }



    private void runLogic() {
        while(currentLives > 0){
            Tick();
            shiftDown();
            addObstacle();
            updateUI();
            Tick();
            shiftDown();
            updateUI();
        }

        //TODO : finish game
        updateLives();
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

        lives = new ImageView[]{
                findViewById(R.id.heart1),
                findViewById(R.id.heart2),
                findViewById(R.id.heart3)
        };

        buttonsArr = new ImageButton[]{
                findViewById(R.id.leftButton),
                findViewById(R.id.rightButton)
        };

        indexMat = new int[imageMat.length + 1][imageMat[0].length];

        //initiation
        for(int i = 0; i <= imageMat.length; ++i){
            for(int j = 0; j < imageMat[0].length; ++j)
                indexMat[i][j] = 0;
        }

        playerPos = 1;

    }

    private void addObstacle(){
        Random random = new Random();
        int val = random.nextInt(indexMat[0].length);
        indexMat[0][val] = 1;
    }

    private void shiftDown(){
        for(int i = 0; i < indexMat.length; ++i){
            for(int j = 0; j < indexMat[i].length; ++j){
                if(indexMat[i][j] == 1){
                    indexMat[i][j] = 0;
                    if(i + 1 < indexMat.length)
                        indexMat[++i][j] = 1;
                    break;
                }
            }
        }
    }

    private void updateUI() {
        buttonsListener();
        for(int i = 0; i < indexMat.length; ++i){
            for(int j = 0; j < indexMat[i].length; ++j) {
                ImageView iw;
                if(i < indexMat.length - 1){//case image mat
                    iw = imageMat[i][j];
                    setVisibility(iw, i, j, false);
                }else{// case image player
                    iw = imageMat[i-1][j];
                    setVisibility(iw, i, j, true);
                }
            }
        }
    }

    private void setVisibility(ImageView iw, int i, int j, boolean changeImage){
        if(!changeImage){
            if(indexMat[i][j] == 0){
                iw.setVisibility(View.INVISIBLE);
            }else{
                iw.setVisibility(View.VISIBLE);
            }
        }else{
            iw.setVisibility(View.INVISIBLE);
            if(indexMat[i][j] == 1){
                if(checkCrash(i, j)){
                    updateLives();
                    //TODO : vibrate?
                    //TODO: fading player?
                }
            }

        }

    }

    private boolean checkCrash(int i, int j){
        //TODO: indexMat necessary?
        return indexMat[i][j] == 1 && imagePlayer[j].getVisibility() == View.VISIBLE;
    }

    private void buttonsListener(){
        for(int i = 0; i < buttonsArr.length; ++i){
            final int buttonIndex = i;
            buttonsArr[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    move(buttonIndex);
                }
            });
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
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //Log.d("pttt",  " Thread=" + Thread.currentThread().getName());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.d("pttt",  " Thread=" + Thread.currentThread().getName());
//
//                    }
//                });
            }
        }, 0, SECOND);
    }

    private void StopTick(){
        timer.cancel();
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
        if(currentLives > 0)
            lives[currentLives--].setVisibility(View.INVISIBLE);
        else{
            StopTick();
            Toast.makeText(getApplicationContext(),"Game Over", Toast.LENGTH_LONG).show();
        }
    }
}