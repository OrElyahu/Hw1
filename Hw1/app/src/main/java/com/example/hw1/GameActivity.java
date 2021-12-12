package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements Serializable {

    private ImageView[][] imageMat;
    private ImageView[] imageLives;
    private ImageView[] imagePlayer;
    private ImageView[] buttonsArr;

    private int[][] indexMat;
    private final int MAX_LIVES = 3;
    private int currentLives;
    private final int SECOND = 1000;
    private final int SLOW = 1000;
    private final int FAST = 500;
    private int timer = SLOW;
    private Handler handler;
    private int playerPos;
    private static CallBack_Score callBackScore;
    private TextView panel_LBL_score;
    int score;
    private MediaPlayer soundWin;
    private MediaPlayer soundLose;
    private final String SENSOR_TYPE = "SENSOR_TYPE";
    private SensorManager sensorManager;
    private Sensor accSensor;
    private boolean isSensor;

    public static String KEY_GAME = "Game";


    public GameActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

//        Toast.makeText(getApplicationContext(),
//                getIntent().getStringExtra(MainMenu.KEY_MAIN),
//                Toast.LENGTH_LONG).show();

        initGame();
        findViews();
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

    @Override
    protected void onResume() {
        super.onResume();
        if(isSensor)
            sensorManager.registerListener(accSensorEventListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isSensor)
            sensorManager.unregisterListener(accSensorEventListener);
    }

    public boolean isSensorExist(int sensorType) {
        return (sensorManager.getDefaultSensor(sensorType) != null);
    }

    private void runLogic() {
        if(currentLives > 0){
            updateUI();
            Tick();
        }
    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private SensorEventListener accSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            DecimalFormat df = new DecimalFormat("##.##");
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            Log.d("Sensor", "x = " + df.format(x) + ", y = " + df.format(y) + ", z = " + df.format(z));
            if(x < -6.5)//case right
                move(false);
            else if( x > 6.5)//case left
                move(true);

            if(y > -7)
                timer = FAST;
            else if(y < 7)
                timer = SLOW;

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void findViews() {
        if(isSensor){
            imageMat = new ImageView[][]{
                    {findViewById(R.id.image00), findViewById(R.id.image01), findViewById(R.id.image02), findViewById(R.id.image03), findViewById(R.id.image04)},
                    {findViewById(R.id.image10), findViewById(R.id.image11), findViewById(R.id.image12), findViewById(R.id.image13), findViewById(R.id.image14)},
                    {findViewById(R.id.image20), findViewById(R.id.image21), findViewById(R.id.image22), findViewById(R.id.image23), findViewById(R.id.image24)},
                    {findViewById(R.id.image30), findViewById(R.id.image31), findViewById(R.id.image32), findViewById(R.id.image33), findViewById(R.id.image34)},
                    {findViewById(R.id.image40), findViewById(R.id.image41), findViewById(R.id.image42), findViewById(R.id.image43), findViewById(R.id.image44)}
            };
            imagePlayer = new ImageView[]{
                    findViewById(R.id.image50), findViewById(R.id.image51), findViewById(R.id.image52), findViewById(R.id.image53), findViewById(R.id.image54)
            };
            playerPos = 2;
        }else{
            imageMat = new ImageView[][]{
                    {findViewById(R.id.image01), findViewById(R.id.image02), findViewById(R.id.image03)},
                    {findViewById(R.id.image11), findViewById(R.id.image12), findViewById(R.id.image13)},
                    {findViewById(R.id.image21), findViewById(R.id.image22), findViewById(R.id.image23)},
                    {findViewById(R.id.image31), findViewById(R.id.image32), findViewById(R.id.image33)},
                    {findViewById(R.id.image41), findViewById(R.id.image42), findViewById(R.id.image43)}
            };
            imagePlayer = new ImageView[]{
                    findViewById(R.id.image51), findViewById(R.id.image52), findViewById(R.id.image53)
            };
            buttonsArr = new ImageView[]{
                    findViewById(R.id.image50),
                    findViewById(R.id.image54)
            };
            buttonsArr[0].setImageResource(R.drawable.left);
            buttonsArr[1].setImageResource(R.drawable.right);
            buttonsArr[0].setVisibility(View.VISIBLE);
            buttonsArr[1].setVisibility(View.VISIBLE);
            playerPos = 1;
        }

        imageLives = new ImageView[]{
                findViewById(R.id.heart1),
                findViewById(R.id.heart2),
                findViewById(R.id.heart3)
        };


        indexMat = new int[imageMat.length + 1][imageMat[0].length];
        panel_LBL_score = findViewById(R.id.panel_LBL_score);
        soundWin = MediaPlayer.create(GameActivity.this,R.raw.winsound);
        soundLose = MediaPlayer.create(GameActivity.this,R.raw.losesound);
        if(isSensor)
            initSensor();
        else
            buttonsListener();
    }

    private void getSoundWin(){
        soundWin.start();
    }

    private void getSoundLose(){
        soundLose.start();
    }

    private void initGame() {
        score = 0;
        currentLives = MAX_LIVES;
        isSensor = getIntent().getBooleanExtra("IsSensor",false);
        if(callBackScore!= null){
            callBackScore.setHasChanged(false);

        }

    }

    public static void setCallBack_score(CallBack_Score callBack_score){ callBackScore = callBack_score; }

    private void addObstacle(){
        Random random = new Random();
        int val = random.nextInt(indexMat[0].length);
        if(isLucky())
            indexMat[0][val] = 2;
        else
            indexMat[0][val] = 1;
    }

    private boolean isLucky(){
        Random random = new Random();
        int val = random.nextInt(indexMat[0].length);
        return val == 0;
    }

    private void shiftDown(){
        for(int i = 0; i < indexMat.length; ++i){
            for(int j = 0;j < indexMat[i].length ; ++j){
                if(indexMat[i][j] != 0){
                    int preVal = indexMat[i][j];
                    indexMat[i++][j] = 0;
                    if(i < indexMat.length)
                        indexMat[i][j] = preVal;
                    break;
                }
            }
        }

    }

    private void updateUI() {

        for(int i = 0; i < indexMat.length; ++i){
            for(int j = 0; j < indexMat[i].length; ++j) {
                if(indexMat[i][j] != 0){ // == 1
                    if(i < imageMat.length){    //case image mat
                        changeImage(i,j,indexMat[i][j]);
                        imageMat[i][j].setVisibility(View.VISIBLE);
                    }else{  //case image player
                        if(checkCrash(j))
                            changeAction(i,j,indexMat[i][j]);
                    }

                    if(i > 0)
                        imageMat[i-1][j].setVisibility(View.INVISIBLE);
                    break;
                }
            }
        }

    }

    private void changeImage(int i, int j, int imageVal){
        if(imageVal == 1)
            imageMat[i][j].setImageResource(R.drawable.templar);
        else
            imageMat[i][j].setImageResource(R.drawable.coin);
    }

    private void changeAction(int i, int j, int imageVal){
        if(imageVal == 1) {
            getSoundLose();
            updateLives();
        }
        else{
            getSoundWin();
            updateScore();
        }

    }

    private void updateScore() {
        score++;
        panel_LBL_score.setText("" + score);
    }

    private boolean checkCrash(int pos){
        return pos == playerPos;
    }

    private void buttonsListener(){
        for(int i = 0; i < buttonsArr.length; ++i){
            final int buttonIndex = i;
            buttonsArr[i].setOnClickListener(v -> move(buttonIndex==0));
        }
    }

    private void move(boolean isLeft){
        if(isLeft){ //case left
            if(playerPos > 0){
                imagePlayer[playerPos--].setVisibility(View.INVISIBLE);
                imagePlayer[playerPos].setVisibility(View.VISIBLE);
            }
        }else{ //case right
            if(playerPos < imagePlayer.length - 1){
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
        }, timer);
    }

    private boolean availObstacle(){
        for(int i = 0; i < 2; ++i){
            for(int j = 0; j < indexMat[i].length; ++j){
                if(indexMat[i][j] != 0)
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
            //Toast.makeText(getApplicationContext(),"Game Over", Toast.LENGTH_LONG).show();
            exitGame();
        }

    }

    private void exitGame() {
        cleanScreen();
        if(isSensor)
            sensorManager.unregisterListener(accSensorEventListener);
        else
            stopButtonListener();
        if(callBackScore != null) {
            callBackScore.setScore("" + score);
            callBackScore.setHasChanged(true);

        }
        Toast.makeText(getApplicationContext(),
                        "Game's Over",
                        Toast.LENGTH_LONG).show();

        finish();

    }

    private void stopButtonListener() {
        for (ImageView imageButton : buttonsArr) {
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

