package com.algonquincollege.skai0001.logindb;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivty extends AppCompatActivity {


    private Button resetpassbtn;
    private EditText EMAIL;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_activty);

        // TODO: Get a reference to the Firebase auth object
        mAuth = FirebaseAuth.getInstance();

        init();
        forgetPass();

    }


    public void init() {
        resetpassbtn = findViewById(R.id.resetbtn);
        EMAIL = findViewById(R.id.resetPass);
    } // end of init()



    // ToDo :forget password will send email
    private void forgetPass() {
        resetpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.sendPasswordResetEmail(EMAIL.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPasswordActivty.this, "Email has been sent !", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(ForgetPasswordActivty.this, "ERROR !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

} // end MainActivity

