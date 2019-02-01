package com.algonquincollege.skai0001.logindb;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Welcome extends AppCompatActivity {

    private Button editbtn, displaybtn;
    private TextView display;
    private EditText editemail, editpass;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference users = FirebaseFirestore.getInstance().collection("Users");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static final String TAG = "Edit Account";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    /*initialization*/
    public void init() {
        editbtn = (Button) findViewById(R.id.editbtn);
        displaybtn = (Button) findViewById(R.id.displaybtn);
        display = (TextView) findViewById(R.id.displaydata);
        editemail = (EditText) findViewById(R.id.editemail);
        editpass = (EditText) findViewById(R.id.editpass);
    }

    /* TODO load data from Authentication */
    public void loadData(View v) { // loads data from authentication
        if (user != null) {
            // String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();

            display.setText(email + "\n" + uid + "\n");
            editemail.setText(email);
        }
    }

    /* TODO updates data to Authentication */
    public void updateData(View v) { //updates email and password in authentication

        user.updateEmail(editemail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Email updated.");
                Toast.makeText(Welcome.this, "Data updated!", Toast.LENGTH_SHORT).show();

            }
        });

        user.updatePassword(editpass.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Welcome.this, "Password updated.", Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void loadDataFromDB(View v){
        // Create a reference to the cities collection
       // CollectionReference users = db.collection("Users");

          //  users.whereEqualTo("admin@admin.com","admin");

        //Query query = db.collection("Users").whereEqualTo("test@test.com", true);

       // display.setText(query);


        // display.setText(users.);

        DocumentReference docRef = db.collection("Users").document("EUXPivJzSW5mHq5eBvmN");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        display.setText(document.getData().toString());
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    //firebase.firestore().collection('users').doc(currentUser.uid).set(currentUser)

} // end of Welcome Activity