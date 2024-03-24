package ru.mshindarev.CDR;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CDRParser {
    private static final String regex = "^\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)$";
    private static final Pattern pattern = Pattern.compile(regex);
    private static String getCallType(String transaction) throws IllegalAccessException {
        Matcher matcher = pattern.matcher(transaction);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            throw new IllegalAccessException();
        }
    }

    private static long getPhoneNumber(String transaction) throws IllegalAccessException {
        Matcher matcher = pattern.matcher(transaction);
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(2));
        } else {
            throw new IllegalAccessException();
        }
    }

    private static long getCallBeginTime(String transaction) throws IllegalAccessException {
        Matcher matcher = pattern.matcher(transaction);
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(2));
        } else {
            throw new IllegalAccessException();
        }
    }

    private static long getCallEndTime(String transaction) throws IllegalAccessException {
        Matcher matcher = pattern.matcher(transaction);
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(2));
        } else {
            throw new IllegalAccessException();
        }
    }
}
