package com.example.partyquick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {

    private CountryCodePicker ccp;
    private EditText t1;
    private Button b1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();

        ccp.registerCarrierNumberEditText(t1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ManageOTP.class);
                intent.putExtra("mobile", ccp.getFullNumberWithPlus().replace(" ",""));
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        ccp = findViewById(R.id.ccp);
        t1 = findViewById(R.id.t1);
        b1 = findViewById(R.id.b1);
    }
}
