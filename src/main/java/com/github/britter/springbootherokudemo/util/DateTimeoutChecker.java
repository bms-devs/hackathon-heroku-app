package com.github.britter.springbootherokudemo.util;

import java.util.Date;

public class DateTimeoutChecker {

    private static final long REST_TIMEOUT = 70000; // 70 s
    public static final long WEBSOCKET_TIMEOUT = 360000; // 6 min

    public static boolean dateRestTimeout(Date date) {
        return date == null || new Date().getTime() - date.getTime() > REST_TIMEOUT;
    }

    public static boolean dateWebsocketTimeout(Date date){
        return date == null || new Date().getTime() - date.getTime() > WEBSOCKET_TIMEOUT;
    }


}
