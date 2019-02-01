package com.algonquincollege.skai0001.logindb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private Button login, register;
    private EditText USERNAME, USERPASS;



    private String str ="";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Get a reference to the Firebase auth object
        mAuth = FirebaseAuth.getInstance();

        // TODO: Attach a new AuthListener to detect sign in and out
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "Signed in: " + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "Currently signed out");
                }
            }
        };

        init();
        login();
        register();
    }

    @Override
    public void onStart() {
        super.onStart();
        // TODO: add the AuthListener
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        // TODO: Remove the AuthListener
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void init() {
        login = (Button) findViewById(R.id.loginbtn);
        register = (Button) findViewById(R.id.create_accountbtn);
        USERNAME = (EditText) findViewById(R.id.uname);
        USERPASS = (EditText) findViewById(R.id.pasword);
    } // end of init()


    public void login() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUserIn();
            }
        });
    } // end of login()

    public void register() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
                finish();
            }
        });
    } // end of register()


    private void signUserIn() {
        if (!checkFormFields())
            return;

        String email = USERNAME.getText().toString();
        String password = USERPASS.getText().toString();

        // TODO: sign the user in with email and password credentials
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    str =USERNAME.getText().toString(); // saves the username

                                    Toast.makeText(MainActivity.this, "Signed in", Toast.LENGTH_SHORT)
                                            .show();
                                    Intent intent = new Intent(MainActivity.this, Welcome.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(MainActivity.this, "Sign in failed", Toast.LENGTH_SHORT)
                                            .show();
                                }

                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(MainActivity.this, "Invalid password.", Toast.LENGTH_SHORT)
                                    .show();
                        } else if (e instanceof FirebaseAuthInvalidUserException) {
                            Toast.makeText(MainActivity.this, "No account with this email.", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Toast.makeText(MainActivity.this, "ERROR.", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });

    } // end of signUserIn()


    private boolean checkFormFields() {
        String email, password;

        email = USERNAME.getText().toString();
        password = USERPASS.getText().toString();

        if (email.isEmpty()) {
            USERNAME.setError("Email Required");
            return false;
        }
        if (password.isEmpty()) {
            USERPASS.setError("Password Required");
            return false;
        }

        return true;
    } // end of checkFormFields()


    public String getStr() {
        return str;
    } // to get username

   

} //end of MainActivity class
