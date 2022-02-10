package com.example.partyquick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ManageOTP extends AppCompatActivity {
    private TextInputLayout t2;
    private MaterialButton b2;
    private String phoneNumber;
    private String otpId;
    private FirebaseAuth mAuth;
    private Validator v;
    private boolean isValid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_opt);

        findViews();
        init();
        valid();
        initOTP();



    }

    private void findViews() {
        t2 = findViewById(R.id.t2);
        b2 = findViewById(R.id.b2);
    }

    private void valid(){
        v = Validator.Builder
                .make(t2)
                .addWatcher(new Validator.Watcher_Number("Not a number"))
                .addWatcher(new Validator.Watcher_Exact_Len("Verify code contains 6 digits",6))
                .build();

    }

    private void init(){
        phoneNumber = getIntent().getStringExtra("mobile").toString();
        mAuth = FirebaseAuth.getInstance();
        b2.setOnClickListener(view -> {
            isValid = v.validateIt();
            if(!isValid){
                Toast.makeText(getApplicationContext(),this.v.getError(),Toast.LENGTH_LONG).show();
            }
            else if(t2.getEditText().getText().toString().isEmpty())
                Toast.makeText(getApplicationContext(),"Blank Field can not be processed",Toast.LENGTH_LONG).show();
            else
            {
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpId,t2.getEditText().getText().toString());
                signInWithPhoneAuthCredential(credential);
            }

        });
    }

    private void initOTP()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
                    {
                        otpId =s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            startActivity(new Intent(ManageOTP.this,MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(),"Signing Code Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
