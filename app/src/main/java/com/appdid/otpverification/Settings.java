package com.appdid.otpverification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

import me.fahmisdk6.avatarview.AvatarView;

public class Settings extends AppCompatActivity {

    private TextView mName,mEmail,mPhoneNumber,mLogOut;
    AvatarView mProfileInit;

    private FirebaseAuth mAuth;
    Pojo pojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mName = findViewById(R.id.profile_name);
        mEmail = findViewById(R.id.emailId);
        mPhoneNumber = findViewById(R.id.phoneNumber);
        mLogOut = findViewById(R.id.logout);
        mProfileInit = findViewById(R.id.avatar);

        pojo = new Pojo(Settings.this);


        mName.setText(pojo.getPojo("Name"));
        mEmail.setText(pojo.getPojo("Email"));
        mPhoneNumber.setText(pojo.getPojo("Phone"));
        mProfileInit.bind(pojo.getPojo("Name"),null);


        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();
                Intent intent = new Intent(Settings.this,MainActivity.class);
                pojo.clearData();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}



