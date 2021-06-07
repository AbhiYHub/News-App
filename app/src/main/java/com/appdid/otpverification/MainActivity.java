package com.appdid.otpverification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    TextView textView;
    private Button button;
    Pojo pojo;
    CountryCodePicker ccp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = findViewById(R.id.mob_no);
        button = findViewById(R.id.con);
        textView = findViewById(R.id.signIn);
        pojo = new Pojo(MainActivity.this);

        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(editText);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignIn.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mob = ccp.getFullNumberWithPlus().trim();

                if(mob.isEmpty() || mob.length()<10)
                {
                    editText.setError("Enter valid number");
                    editText.requestFocus();
                    return;
                }

                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                pojo.setPojo("Phone",mob);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() !=null)
        {
            Intent intent = new Intent(this,LastPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
