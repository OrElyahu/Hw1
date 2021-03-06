package com.example.partyquick;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {

    private CountryCodePicker ccp;
    private TextInputLayout t1;
    private TextInputLayout t_password;
    private MaterialButton b1;
    private MaterialButton BTN_Register;
    private Validator v;
    private boolean isValid = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        init();
        valid();
    }

    private void findViews() {
        ccp = findViewById(R.id.ccp);
        t1 = findViewById(R.id.t1);
        t_password = findViewById(R.id.t_password);
        b1 = findViewById(R.id.b1);
        BTN_Register = findViewById(R.id.BTN_Register);
    }

    private void init(){
        ccp.setDefaultCountryUsingNameCode("IL");
        ccp.resetToDefaultCountry();
        ccp.registerCarrierNumberEditText(t1.getEditText());
        b1.setOnClickListener(v -> {
            isValid = this.v.validateIt();
            if(!isValid){
                Toast.makeText(getApplicationContext(),this.v.getError(),Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent(Login.this, ManageOTP.class);
                intent.putExtra("mobile", ccp.getFullNumberWithPlus().replace(" ", ""));
                startActivity(intent);
            }
        });
        BTN_Register.setOnClickListener(v1 -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
            finish();
        });
    }

    private void valid(){
        v = Validator.Builder
                .make(t1)
                .addWatcher(new Validator.Watcher_MaximumOfLetter("Format exception",'-',2))
                .addWatcher(new Validator.Watcher_MinimumOfLetter("Format exception",'-',2))
                .addWatcher(new Validator.Watcher_Exact_Len("Phone must contain 9 digits",11))
                .build();

    }
}
