package com.algonquincollege.skai0001.logindb;

import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ChildrenProfile extends AppCompatActivity {

    private EditText NAME, AGE, displayChildren;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CollectionReference users;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_profile);

        // TODO: Get a reference to the Firebase auth object
        mAuth = FirebaseAuth.getInstance();

        init();

    }



    /*initialization*/
    public void init() {
        NAME =  findViewById(R.id.name);
        AGE =  findViewById(R.id.age);
        displayChildren =  findViewById(R.id.display_children);
    }

    public void addChild(View v) {

        final String name= NAME.getText().toString();
        final String age= AGE.getText().toString();

        FirebaseUser user = mAuth.getCurrentUser();

        String str = user.getEmail();

        users = FirebaseFirestore.getInstance().
                collection("Children");

        /*conditions for values*/
        if (NAME.getText().toString().trim().equals("") || NAME.length() < 2) {
            NAME.setError("First name is required!");

        } else if (AGE.getText().toString().trim().equals("") || AGE.length() <= 0) {
            AGE.setError("Last name is required!");

        } else {

            users.add(new ChildrenAccount(name, age, str)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    Toast.makeText(ChildrenProfile.this, "Data saved to Children Collection", Toast.LENGTH_SHORT).show();

                }
            });



           /* Intent intent = new Intent(CreateAccount.this, MainActivity.class);
            startActivity(intent);
            finish();*/

        }
    } // end of saveData()

    public  void deleteChild(View v){


    }


}
