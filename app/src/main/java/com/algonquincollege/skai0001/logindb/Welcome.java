package com.algonquincollege.skai0001.logindb;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class Welcome extends AppCompatActivity {

    private EditText display_fname, display_lname, display_email, display_pass;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users = FirebaseFirestore.getInstance().collection("Users");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    /*initialization*/
    public void init() {
        display_fname = findViewById(R.id.firstname_data);
        display_lname = findViewById(R.id.lastname_data);
        display_email = findViewById(R.id.email_data);
        display_pass = findViewById(R.id.pass_data);
    }



    /* TODO updates data to Authentication */
    public void updateData(View v) { //updates email and password in authentication
        String docName = user.getEmail();

        String FIRSTNAME = display_fname.getText().toString();
        String LASTNAME = display_lname.getText().toString();
        final String EMAIL = display_email.getText().toString();
        final String PASSWORD = display_pass.getText().toString();


        DocumentReference doc = db.collection("Users").document(docName);

        /*conditions for values*/
        if (FIRSTNAME.trim().equals("") || FIRSTNAME.length() < 2) {
            display_fname.setError("First name is required!");

        } else if (LASTNAME.trim().equals("") || LASTNAME.length() < 2) {
            display_lname.setError("Last name is required!");
        } else if (EMAIL.trim().equals("")) {
            display_email.setError("Email name is required!");
        } else if (PASSWORD.trim().equals("") || PASSWORD.length() < 6) {
            display_pass.setError("Password is required and should be over 6!");

        } else if (!isEmailValid(EMAIL)) {
            display_email.setError("Please check you email!");

        } else {

            doc.update(
                    "firstname", FIRSTNAME,
                    "lastName", LASTNAME,
                    "email", EMAIL
            ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        user.updateEmail(EMAIL).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                user.updatePassword(PASSWORD);
                            }
                        });
                        Toast.makeText(Welcome.this, "Data updated!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Welcome.this, "ERROR!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    } // end of updateData()

// TODO: retrieve data from FireStore DB
    public void loadDataFromDB(View v) {

        String email = user.getEmail();
        FirebaseFirestore.getInstance().collection("Users").whereEqualTo("email", email).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String email, fname, lname;
                        for (QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots) {
                            Accounts accounts = documentSnapshots.toObject(Accounts.class);
                            email = accounts.getEmail();
                            fname = accounts.getFirstName();
                            lname = accounts.getLastName();

                            display_fname.setText(fname);
                            display_lname.setText(lname);
                            display_email.setText(email);
                            display_pass.setText("");

                        }
                    }
                });
    } // end of loadDataFromDB()

    // TODO: checks for email validation
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

} // end of Welcome Activity