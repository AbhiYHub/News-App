package com.appdid.otpverification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main3Activity extends AppCompatActivity {

    private Button button;
    private EditText mUserName,mEmailId,mPassword,mPhoneNumber;

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    ProgressDialog progressDialog;
    Pojo pojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        pojo = new Pojo(Main3Activity.this);

        button = findViewById(R.id.register);
        mUserName = findViewById(R.id.user_name);
        mEmailId = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhoneNumber= findViewById(R.id.edt_number);
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Main3Activity.this);
        progressDialog.setMessage("Registering please wait...");

        mPhoneNumber.setText(pojo.getPojo("Phone"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUserName.getText().length()==0)
                {
                    mUserName.setError("Enter User Name");
                    mUserName.requestFocus();
                    return;
                }
                if(mEmailId.getText().length()==0)
                {
                    mEmailId.setError("Enter Email Id");
                    mEmailId.requestFocus();
                    return;
                }
                if(mPassword.getText().length()==0)
                {
                    mPassword.setError("Enter Password");
                    mPassword.requestFocus();
                    return;
                }

                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(mEmailId.getText().toString().trim(),mPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(Main3Activity.this,LastPage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            progressDialog.dismiss();
                            pojo.setPojo("Email",mEmailId.getText().toString().trim());
                            pojo.setPojo("Name",mUserName.getText().toString().trim());
                            setOnline("Name",mUserName.getText().toString().trim());
                            setOnline("Email",mEmailId.getText().toString().trim());
                            setOnline("Phone",pojo.getPojo("Phone"));
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Main3Activity.this,"Registration Unsuccessful",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });


            }
        });

    }

    void setOnline(String key,String value)
    {
        FirebaseUser user = mAuth.getCurrentUser();
        String UserId = user.getUid();
        databaseReference.child("Users").child(UserId).child(key).setValue(value);
    }

}
