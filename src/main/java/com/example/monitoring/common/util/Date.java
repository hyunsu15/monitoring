package com.example.monitoring.common.util;

import com.example.monitoring.common.exception.DateParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public enum Date {
    DAY("D", 60 * 24), HOUR("H", 60), MINUTE("M", 1);

    private String format;
    private int minute;

    Date(String format, int minute) {
        this.format = format;
        this.minute = minute;
    }

    public static LocalDateTime toLocalDate(LocalDateTime now, String date) {
        try {
            long minutes = parseDate(date);
            return now.minus(minutes, ChronoUnit.MINUTES);
        } catch (NullPointerException | NumberFormatException e) {
            throw new DateParseException();
        }
    }

    private static long parseDate(String date) {
        long times = 0;
        for (Date value : values()) {
            String upperFormat = value.format.toUpperCase();
            String lowerFormat = value.format.toLowerCase();
            if (date.indexOf(upperFormat) == -1 && date.indexOf(lowerFormat) == -1) {
                continue;
            }
            int formatIndex = Math.max(date.indexOf(upperFormat), date.indexOf(lowerFormat));
            long minutes = Long.parseLong(date.substring(0, formatIndex)) * value.minute;
            times += minutes;
            date = date.substring(formatIndex + 1);
        }
        if (!date.isEmpty()) {
            throw new DateParseException();
        }
        return times;
    }
}
