package com.example.partyquick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class Register extends AppCompatActivity {

    private CountryCodePicker ccp_register;
    private TextInputLayout t1_register;
    private TextInputLayout t_name_register;
    private TextInputLayout t_password_register;
    private MaterialButton b1_register;
    private MaterialButton BTN_Login;
    private Validator v;
    private boolean isValid = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViews();
        init();
        valid();
    }

    private void findViews() {
        ccp_register = findViewById(R.id.ccp_register);
        t1_register = findViewById(R.id.t1_register);
        t_name_register = findViewById(R.id.t_name_register);
        t_password_register = findViewById(R.id.t_password_register);
        b1_register = findViewById(R.id.b1_register);
        BTN_Login = findViewById(R.id.BTN_Login);
    }

    private void init(){
        ccp_register.setDefaultCountryUsingNameCode("IL");
        ccp_register.resetToDefaultCountry();
        ccp_register.registerCarrierNumberEditText(t1_register.getEditText());
        b1_register.setOnClickListener(v -> {
            isValid = this.v.validateIt();
            if(!isValid){
                Toast.makeText(getApplicationContext(),this.v.getError(),Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent = new Intent(Register.this, ManageOTP.class);
                intent.putExtra("mobile", ccp_register.getFullNumberWithPlus().replace(" ", ""));
                startActivity(intent);
            }
        });
        BTN_Login.setOnClickListener(v1 -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            finish();
        });
    }

    private void valid(){
        v = Validator.Builder
                .make(t1_register)
                .addWatcher(new Validator.Watcher_MaximumOfLetter("Format exception",'-',2))
                .addWatcher(new Validator.Watcher_MinimumOfLetter("Format exception",'-',2))
                .addWatcher(new Validator.Watcher_Exact_Len("Phone must contain 9 digits",11))
                .build();

    }
}
