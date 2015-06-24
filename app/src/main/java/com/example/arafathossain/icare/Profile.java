package com.example.arafathossain.icare;

/**
 * Created by Arafat Hossain on 6/9/2015.
 */
public class Profile {
    private String profileName;
    private String userName;
    private String email;
    private String contactNo;
    private String height;
    private String weight;
    private String bloodGroup;
    private String dateOfBirth;
    private String gender;
    private int id;

    public Profile(int id,String profileName, String userName, String email, String contactNo, String height, String weight, String bloodGroup, String dateOfBirth,String gender) {
        this.id = id;
        this.profileName = profileName;
        this.userName = userName;
        this.email = email;
        this.contactNo = contactNo;
        this.height = height;
        this.weight = weight;
        this.bloodGroup = bloodGroup;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public Profile() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
