package com.dallanosm.dshproject;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("periods");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Period period = dataSnapshot.getValue(Period.class);
                    if (period != null) {
                        setAlarms(period.isSunmer(), period.getInitDate(), period.getFinishDate());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setAlarms(Boolean isSummer, Long initDate, Long finishDate) {
        Calendar initCalendar = Calendar.getInstance();
        initCalendar.setTime(new Date(initDate));
        Calendar finishCalendar = Calendar.getInstance();
        finishCalendar.setTime(new Date(finishDate));

        initCalendar.set(Calendar.HOUR_OF_DAY, 0);
        initCalendar.set(Calendar.MINUTE, 0);
        initCalendar.set(Calendar.SECOND, 0);

        finishCalendar.set(Calendar.HOUR, 23);
        finishCalendar.set(Calendar.MINUTE, 59);
        finishCalendar.set(Calendar.HOUR, 59);

        for (int i = 0; i < AlarmUtils.daysBetween(initCalendar, finishCalendar); i++) {
            AlarmUtils.scheduleAlarm(this,
                    AlarmUtils.getEnableHour(isSummer, initCalendar.getTimeInMillis() + i * AlarmUtils.getMilisecondsOfADay()),
                    true);

        }
    }
}
