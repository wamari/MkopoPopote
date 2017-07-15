package com.harlertechnologies.mkopopopote;

/**
 * Created by wamari on 7/15/17.
 */

public class User {

    String userId;
    String firstName;
    String lastName;
    String idno;
    String email;
    String dob;
    String gender;

    //define a blank constructor
    public User(){

    }

    public User(String userId, String firstName, String lastName, String idno, String email, String dob, String gender) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idno = idno;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
    }

    //generate getters


    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdno() {
        return idno;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }
}
