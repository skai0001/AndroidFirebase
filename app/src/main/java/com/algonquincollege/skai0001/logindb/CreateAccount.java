package com.algonquincollege.skai0001.logindb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;



public class CreateAccount extends AppCompatActivity {


    private Button backbtn;
    private EditText FIRSTNAME, LASTNAME, USERNAME, EMAIL, PASSWORD;
    private static final String TAG = "Create Account";
    private CollectionReference users;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

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


        /*initialization*/
    public void init() {
        backbtn = (Button) findViewById(R.id.backbtn);
        FIRSTNAME = (EditText) findViewById(R.id.firstname);
        LASTNAME = (EditText) findViewById(R.id.lastname);
        USERNAME = (EditText) findViewById(R.id.username);
        EMAIL = (EditText) findViewById(R.id.email);
        PASSWORD = (EditText) findViewById(R.id.pwd);

    }

    public void login() {
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void saveData(View v) {

        String fname = FIRSTNAME.getText().toString();
        String lname = LASTNAME.getText().toString();
        String uname = USERNAME.getText().toString();
        String email = EMAIL.getText().toString();
        String pass = PASSWORD.getText().toString();


        users = FirebaseFirestore.getInstance().
                collection("Users");

        /*conditions for values*/
        if( FIRSTNAME.getText().toString().trim().equals("") || FIRSTNAME.length() < 2 ) {
            FIRSTNAME.setError("First name is required!");

        }else if( LASTNAME.getText().toString().trim().equals("") || LASTNAME.length() < 2) {
            LASTNAME.setError("Last name is required!");
        } else if( USERNAME.getText().toString().trim().equals("") || USERNAME.length() < 2) {
            USERNAME.setError("User name is required!");
        }else if(EMAIL.getText().toString().trim().equals("")) {
            EMAIL.setError("Email name is required!");
        }else if( PASSWORD.getText().toString().trim().equals("") || PASSWORD.length() < 6) {
            PASSWORD.setError("Password is required and should be over 6!");

        } else if (!isEmailValid(email)){
            EMAIL.setError("Please check you email!");

        } else {


            // add to authentication
            mAuth.createUserWithEmailAndPassword(email, pass);


            // add to Firestore
            users.add(new Accounts(fname, lname, uname, email, pass)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(CreateAccount.this, "Data saved to FireStore", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateAccount.this, "Error!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                }
            });



            Intent intent = new Intent(CreateAccount.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
    } // end of saveData()

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
} // end of CreateAccount Activity
