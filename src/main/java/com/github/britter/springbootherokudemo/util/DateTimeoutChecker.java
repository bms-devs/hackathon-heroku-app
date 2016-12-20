package com.github.britter.springbootherokudemo.util;

import java.util.Date;

public class DateTimeoutChecker {

    private static final long TIMEOUT = 70000;

    public static boolean dateTimeout(Date date) {
        return date == null || new Date().getTime() - date.getTime() > TIMEOUT;
    }
}
