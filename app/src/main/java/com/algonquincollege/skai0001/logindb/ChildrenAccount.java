package com.algonquincollege.skai0001.logindb;

public class ChildrenAccount  {




    String name;
    String age;
    String parentId;

    public ChildrenAccount() {
        //public no-arg constructor needed
    }


    public ChildrenAccount(String name, String age, String parentId) {

        this.name = name;
        this.age = age;
        this.parentId = parentId;

    }


    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

}
