package com.appdid.otpverification;

import android.arch.core.executor.TaskExecutor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity {


    private String verificcationId;
    private ProgressBar progressBar;
    private Button b1;
    private EditText editText;
    String phonenumber;
    private Pojo pojo;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();

        editText = findViewById(R.id.otp);

        progressBar = findViewById(R.id.progressbar);

        pojo = new Pojo(Main2Activity.this);

        phonenumber = pojo.getPojo("Phone");

        sendVerificationCode(phonenumber);

        b1 = findViewById(R.id.conf);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editText.getText().toString().trim();
                if(code.isEmpty() || code.length()<6)
                {
                    editText.setError("Enter code...");
                    editText.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });

    }

    private void verifyCode(String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificcationId,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pojo.setPojo("Phone",phonenumber);
                    startActivity(intent);

                } else {

                    Toast.makeText(Main2Activity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void sendVerificationCode(String number){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificcationId = s;


        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if(code!=null)
            {
                verifyCode(code);
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(Main2Activity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    };
}
