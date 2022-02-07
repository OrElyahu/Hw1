package com.example.partyquick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private Fragment_Search fragmentSearch;

    private MaterialButton BTN_DISCOVER;
    private MaterialButton BTN_SEARCH;
    private MaterialButton BTN_MY_ORDERS;
    private MaterialButton BTN_ADD_EVENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BTN_SEARCH = findViewById(R.id.BTN_SEARCH);
        BTN_SEARCH.setOnClickListener(v -> {
            fragmentSearch = new Fragment_Search();
            fragmentSearch.setActivity(this);
            getSupportFragmentManager().beginTransaction().add(R.id.frame1, fragmentSearch).commit();
        });




    }

}