package com.example.arafathossain.icare;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProfileValidation {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String HEIGHT_PATTERN = "^[0-9]{1,3}(\\.[0-9]+)?\\s?(ft|m|cm)$";
    private static final String WEIGHT_PATTERN = "^[0-9]{1,3}(\\.[0-9]+)?\\s?(kg|lb|pound|gm)$";

    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validateProfileName(String profileName) {
        if (profileName == null || profileName.isEmpty()) return false;
        return ApplicationMain.getDatabase().checkProfileName(profileName);
    }

    public static boolean validateUserNAme(String userName) {
        if (userName == null || userName.isEmpty()) return false;
        else return true;
    }

    public static boolean validateDateOfBirth(String dob) {
        if (dob == null || dob.isEmpty()) return false;
        else return true;
    }

    public static boolean validateHeight(String height) {
        if (height == null || height.isEmpty()) return false;
        Pattern heightPattern = Pattern.compile(HEIGHT_PATTERN);
        Matcher matcher = heightPattern.matcher(height);
        return matcher.matches();
    }

    public static boolean validateWeight(String weight) {
        if (weight == null || weight.isEmpty()) return false;
        Pattern weightPattern = Pattern.compile(WEIGHT_PATTERN);
        Matcher matcher = weightPattern.matcher(weight);
        return matcher.matches();
    }
}
