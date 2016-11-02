package com.fearnot.snapp;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by phoenixcampos01 on 11/2/16.
 */

public class RegexHelper {
    public static boolean isNumeric(String number) {
        Pattern pattern;
        Matcher matcher;
        final String DATE_PATTERN = "^(0|[1-9][0-9]*)$";
        pattern = Pattern.compile(DATE_PATTERN);
        matcher = pattern.matcher(number);

        return matcher.matches();
    }

    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static boolean isValidDate(final String date) {
        Pattern pattern;
        Matcher matcher;
        final String DATE_PATTERN = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
        pattern = Pattern.compile(DATE_PATTERN);
        matcher = pattern.matcher(date);

        return matcher.matches();
    }
}
