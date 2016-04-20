package app.glintcarwash.com.glintworkerapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimeDialog extends Activity {
    Button btnCreate;
    EditText edtEndTime,edtStartTime;
    TimePicker timePicker1;
    static final int TIME_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID_2 = 999;
    private int hour,hour2;
    private int minute,minute2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.time_dialog);
        edtEndTime = (EditText) findViewById(R.id.edtEndTime);
        edtStartTime = (EditText) findViewById(R.id.edtStartTime);
        btnCreate = (Button) findViewById(R.id.btnCreate);

        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);

        edtStartTime.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v == timePicker1) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        setCurrentTimeOnView();
                        addListenerOnButton();
                    }
                }
                return false;
            }
        });

        edtEndTime.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v == timePicker1) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        setCurrentTimeOnView();
                        addListenerOnButton2();
                    }
                }
                return false;
            }
        });

    }


    public void setCurrentTimeOnView() {

        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);

        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // set current time into textview
        // edtTime.setText(new StringBuilder().append(pad(hour)).append(":")
        // .append(pad(minute)));

        // set current time into timepicker
        timePicker1.setCurrentHour(hour);
        timePicker1.setCurrentMinute(minute);

    }

    public void addListenerOnButton() {
        showDialog(TIME_DIALOG_ID);

    }
    public void addListenerOnButton2() {
        showDialog(TIME_DIALOG_ID_2);

    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour,
                              int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;

            // set current time into textview
            edtStartTime.setText(new StringBuilder().append(pad(hour)).append(":")
                    .append(pad(minute)));

            timePicker1.setCurrentHour(hour);
            timePicker1.setCurrentMinute(minute);

        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener2 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour,
                              int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;

            // set current time into textview
            edtEndTime.setText(new StringBuilder().append(pad(hour)).append(":")
                    .append(pad(minute)));

            timePicker1.setCurrentHour(hour);
            timePicker1.setCurrentMinute(minute);

        }
    };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == TIME_DIALOG_ID) {
            return new TimePickerDialog(this, timePickerListener, hour, minute,
                    false);
        }else if (id == TIME_DIALOG_ID_2) {
            return new TimePickerDialog(this, timePickerListener2, hour, minute,
                    false);
        }
        return null;
    }

}
