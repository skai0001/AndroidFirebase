package com.algonquincollege.skai0001.logindb;



public class Accounts {


    private String firstName, lastName, email;

    public Accounts() {
        //public no-arg constructor needed
    }

    public Accounts(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }



}
