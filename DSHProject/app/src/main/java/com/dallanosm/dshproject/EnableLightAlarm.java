package com.dallanosm.dshproject;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.util.Date;

public class EnableLightAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PeripheralManagerService peripheralManagerService = new PeripheralManagerService();
        try {
            Gpio gpio = peripheralManagerService.openGpio("BCM18");
            gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            gpio.setActiveType(Gpio.ACTIVE_LOW);
            gpio.setValue(true);
            gpio.close();
            Date date = new Date();
            AlarmUtils.scheduleAlarm(context, AlarmUtils.getDisableHour(true, date.getTime()), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
