package com.algonquincollege.skai0001.logindb;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

public class Accounts {


    private String firstName, lastName, userName, email, password;
    private String documentId;

    public Accounts() {
        //public no-arg constructor needed
    }

    public Accounts(String firstName, String lastName, String userName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getUserName() {
        return userName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

}
