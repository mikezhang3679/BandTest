package com.example.bandtest.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.widget.DatePicker;

import java.util.Calendar;


public class CustomDateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private boolean showTime;
    public interface dateListener{
        void returnDate(int year, int month, int dayOfMonth,boolean showTime);
    }

    dateListener listener;

    public void setListener(dateListener li,boolean showTime)
    {
        listener=li;
        this.showTime=showTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return  dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (listener != null)
        {
            listener.returnDate(year, month, dayOfMonth,showTime);

        }

    }
}

