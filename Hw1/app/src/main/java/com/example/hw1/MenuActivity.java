package com.example.hw1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Objects;

public class MenuActivity extends AppCompatActivity implements Serializable {

    private Button BNT_2_BTN;
    private Button BNT_Sensors;
    private Button BNT_Score;
    private TextInputLayout name;
    private String scoreFromGame = "";
    private DataManager dataManager;
    private Boolean hasChangedFromGame = false;


    public static String KEY_SENSOR_MODE = "IsSensor";

    public MenuActivity() { }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViews();
        (new LocationService(MenuActivity.this)).showSettingsAlert();
        buttonsListener();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(callBackScore != null)
        {
            if(hasChangedFromGame){
                getResults();
                hasChangedFromGame = false;
            }
        }
        Objects.requireNonNull(name.getEditText()).setText("");
    }

    private void findViews() {
        BNT_2_BTN = findViewById(R.id.BNT_2_BTN);
        BNT_Sensors = findViewById(R.id.BNT_Sensors);
        BNT_Score = findViewById(R.id.BNT_Score);
        name = findViewById(R.id.TXT_NAME);
        dataManager = new DataManager();
    }

    private void buttonsListener() {
        BNT_2_BTN.setOnClickListener(v -> {
            changeActivity(0);
        });

        BNT_Sensors.setOnClickListener(v -> {
            changeActivity(1);
        });

        BNT_Score.setOnClickListener(v -> {
            changeActivity(2);
        });

    }

    private boolean isNameFilled(){
        if(Objects.requireNonNull(name.getEditText()).getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),
                            "What's Your name?",
                            Toast.LENGTH_LONG).show();
            return false;
        }
            return true;
    }

    private void changeActivity(int choice){

        Intent myIntent = null;
        if(choice != 2){
            if(!isNameFilled())
                return;
            myIntent = new Intent(this, GameActivity.class);
            myIntent.putExtra(KEY_SENSOR_MODE, choice != 0);
            //myIntent.putExtra(KEY_INTERFACE, (Parcelable) callBackScore);
            GameActivity.setCallBack_score(callBackScore);
        }else {
            myIntent = new Intent(this, ScoreActivity.class);
            ScoreActivity.setCallBack_Update(callBackUpdate);
        }
        startActivity(myIntent);


    }

    CallBack_Score callBackScore = new CallBack_Score() {
        @Override
        public void setScore(String score) {
            scoreFromGame = score;
        }

        @Override
        public void setHasChanged(Boolean hasChanged) {
            hasChangedFromGame = hasChanged;
        }

    };

    private void getResults(){
        dataManager = new Gson().fromJson(MSP.getMe().getString("DATA_MANAGER",""),DataManager.class);
        LocationService ls = new LocationService(MenuActivity.this);
        double lon,lat;
        if(ls.canGetLocation()){
            lon = ls.getLongitude();
            lat = ls.getLatitude();
        }else {
            ls.showSettingsAlert();
            lon = ls.getLongitude();
            lat = ls.getLatitude();
        }
        Log.d("LocationService", "Lat : " + lat +" , Lon : " + lon);

        dataManager.addPlayer(new Player()
            .setName(Objects.requireNonNull(name.getEditText()).getText().toString())
            .setScore(scoreFromGame)
            .setLat(lat)
            .setLon(lon));

        MSP.getMe().putString("DATA_MANAGER", new Gson().toJson(dataManager));
    }

    CallBack_Update callBackUpdate = new CallBack_Update() {
        @Override
        public void setData(DataManager dataManager) {

        }

        @Override
        public DataManager getData() {
            dataManager = new Gson().fromJson(MSP.getMe().getString("DATA_MANAGER",""),DataManager.class);
            return dataManager;
        }

    };


}
