package com.example.bandtest.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * Created by Harry on 2018/1/15.
 */

public  class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    private TimeListener timeListener;
    public interface TimeListener {
        void returnTime(int hour,int minute);
    }

    public void setTimeLisenter(TimeListener lisenter){
        this.timeListener = lisenter;

    }
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        if (timeListener!=null) {
            timeListener.returnTime(i,i1);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this,hour,minute, DateFormat.is24HourFormat(getActivity()));
    }
}
