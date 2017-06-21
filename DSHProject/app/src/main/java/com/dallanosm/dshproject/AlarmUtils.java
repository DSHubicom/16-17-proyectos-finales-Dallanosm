package com.dallanosm.dshproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AlarmUtils {

    private static final int HOURS = 24;

    private static final int MINUTES = 60;

    private static final int SECONDS = 60;

    private static final int MILISECONDS = 1000;

    private static final int IN_SUMMER = 22;

    private static final int IN_WINTER = 18;

    private static final int MINUTES_ON_A_HOUR = 59;

    public static void scheduleAlarm(Context context, Long time, boolean enable) {
        Intent intentAlarm = new Intent(context, (enable) ? EnableLightAlarm.class : DisableLightAlarm.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Date inTwoMinutes = new Date(time);
        alarmManager.set(AlarmManager.RTC_WAKEUP, inTwoMinutes.getTime(),
                PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public static long daysBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
    }

    public static Long getEnableHour(boolean isSummer, Long initDate) {
        Long enableTime;
        if (isSummer) {
            enableTime = initDate + IN_SUMMER * MINUTES * SECONDS * MILISECONDS + getRandomMinutes();
        } else {
            enableTime = initDate + IN_WINTER * MINUTES * SECONDS * MILISECONDS + getRandomMinutes();
        }
        return enableTime;
    }

    public static Long getDisableHour(boolean isSummer, Long initDate) {
        return initDate + getRandomHour(isSummer) + getRandomMinutes();
    }

    public static long getRandomHour(boolean isSummer) {
        Random r = new Random();
        return r.nextInt((isSummer) ? 2 : 6) * MINUTES * SECONDS * MILISECONDS;
    }

    public static long getRandomMinutes() {
        Random r = new Random();
        return 30 * SECONDS + MILISECONDS + r.nextInt(MINUTES_ON_A_HOUR) * SECONDS * MILISECONDS;
    }

    public static long getMilisecondsOfADay() {
        return HOURS * MINUTES * SECONDS * MILISECONDS;
    }

}
