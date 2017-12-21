package com.qinnovation.sample.utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mohamed.ibrahim on 22-Mar-17.
 */

public class ValidationUtil {
    private static final ValidationUtil ourInstance = new ValidationUtil();

    public static ValidationUtil getInstance() {
        return ourInstance;
    }

    private ValidationUtil() {
    }

    public boolean validatePANNumber(String panNum) {
//        boolean isValid = false;
        if (TextUtils.isEmpty(panNum))
            return false;
        //[A-Z]{5}\\d{4}[A-Z]{1}
//        String s = "ABCDE1234F"; // get your editext value here
//        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Pattern pattern = Pattern.compile("[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}");

        Matcher matcher = pattern.matcher(panNum);
        // Check if pattern matches
        return matcher.matches();
    }

    public boolean validateAadharNumber(String aadharNumber) {
        if (TextUtils.isEmpty(aadharNumber))
            return false;
//        Pattern aadharPattern = Pattern.compile("\\d{4}\\s\\d{4}\\s\\d{4}");
        Pattern aadharPattern = Pattern.compile("\\d{12}");
        return aadharPattern.matcher(aadharNumber).matches();
    }

    public boolean validateMobileNumber(String mobileNumber) {
        if (TextUtils.isEmpty(mobileNumber))
            return false;
        Pattern mobilePattern = Pattern.compile("\\d{10}");
        return mobilePattern.matcher(mobileNumber).matches();
    }

    public boolean validateEmail(String emailId) {
        return !TextUtils.isEmpty(emailId) && Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
    }

    public boolean validateLandLineNumber(String landLineNumber) {
        if (TextUtils.isEmpty(landLineNumber))
            return false;
        Pattern mobilePattern = Pattern.compile("[0-9]\\d{1,4}-\\d{6,8}");
        return mobilePattern.matcher(landLineNumber).matches();
    }

    public boolean validatePostalCode(String postalCode) {
        if (TextUtils.isEmpty(postalCode))
            return false;
        Pattern mobilePattern = Pattern.compile("\\d{6}");
        return mobilePattern.matcher(postalCode).matches();
    }
}
