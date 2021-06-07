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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    private EditText mEmail,mPassword;
    private Button mLogin;
    String name,email,phone;
    private DatabaseReference myRef;
    FirebaseAuth mFirebaseAuth;
    ProgressDialog progressDialog;
    Pojo pojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mFirebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(SignIn.this);
        mLogin = findViewById(R.id.btn_login);

        myRef = FirebaseDatabase.getInstance().getReference();

        pojo = new Pojo(SignIn.this);


        progressDialog.setMessage("Logging In please wait...");
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                       progressDialog.show();
                       mFirebaseAuth.signInWithEmailAndPassword(mEmail.getText().toString().trim(),mPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful())
                               {
                                   Intent intent = new Intent(SignIn.this,LastPage.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   progressDialog.dismiss();
                                   getOnlineData();
                                   startActivity(intent);
                               }
                               else {
                                   Toast.makeText(SignIn.this,"Log In Unsuccessful",Toast.LENGTH_SHORT).show();
                                   progressDialog.dismiss();
                               }
                           }
                       });
            }
        });
    }

    void getOnlineData(){

        String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRef.child("Users").child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                phone = dataSnapshot.child("Phone").getValue().toString();
                email = dataSnapshot.child("Email").getValue().toString();

                pojo.setPojo("Name",dataSnapshot.child("Name").getValue().toString());
                pojo.setPojo("Phone",phone);
                pojo.setPojo("Email",email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}