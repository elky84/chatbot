package com.elky.chatbot.util;

import java.util.Date;

public class DateUtil {
    public static Date Now() {
        return Date.from(java.time.ZonedDateTime.now().toInstant());
    }
}
