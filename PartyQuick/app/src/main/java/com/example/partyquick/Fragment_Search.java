package com.example.partyquick;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class Fragment_Search extends Fragment {

    private TextInputLayout frame_discover_EDT_search;
    private MaterialButton frame_discover_BTN_search;
    private ImageView frame_discover_result;

    private AppCompatActivity activity;


    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        findViews(view);


        return view;
    }





    private void findViews(View view) {
        frame_discover_EDT_search = view.findViewById(R.id.frame_discover_EDT_search);
        frame_discover_BTN_search = view.findViewById(R.id.frame_discover_BTN_search);
        frame_discover_result = view.findViewById(R.id.frame_discover_result);
    }
}