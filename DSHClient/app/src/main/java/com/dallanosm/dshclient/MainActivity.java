package com.dallanosm.dshclient;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference database;

    //UI References
    private EditText fromDate;

    private EditText toDate;

    private Long from;

    private Long to;

    private Button setAlarms;

    private DatePickerDialog fromDateDialog;

    private DatePickerDialog toDateDialog;

    private SimpleDateFormat dateFormatter;

    private CheckBox isSummer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", new Locale("es", "ES"));

        findViews();

        setDateTimeField();
        database = FirebaseDatabase.getInstance().getReference("periods");
    }

    private void findViews() {
        fromDate = (EditText) findViewById(R.id.etxt_fromdate);
        fromDate.setInputType(InputType.TYPE_NULL);
        fromDate.requestFocus();

        toDate = (EditText) findViewById(R.id.etxt_todate);
        toDate.setInputType(InputType.TYPE_NULL);

        isSummer = (CheckBox) findViewById(R.id.is_summer);
        setAlarms = (Button) findViewById(R.id.set_alarms);
    }

    private void setDateTimeField() {
        fromDate.setOnClickListener(this);
        toDate.setOnClickListener(this);
        setAlarms.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDate.setText(dateFormatter.format(newDate.getTime()));
                from = newDate.getTimeInMillis();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDate.setText(dateFormatter.format(newDate.getTime()));
                to = newDate.getTimeInMillis();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setAlarms() {
        database.setValue(new Period(from, to, isSummer.isChecked()));
    }

    @Override
    public void onClick(View view) {
        if (view == fromDate) {
            fromDateDialog.show();
        } else if (view == toDate) {
            toDateDialog.show();
        } else if (view == setAlarms) {
            setAlarms();
        }
    }

}
