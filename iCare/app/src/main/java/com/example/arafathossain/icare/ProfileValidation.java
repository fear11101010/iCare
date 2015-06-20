package com.example.arafathossain.icare;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Arafat Hossain on 6/16/2015.
 */
public class ProfileValidation {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

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
}
