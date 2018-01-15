package com.example.bandtest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.bandtest.R;
import com.example.bandtest.bean.RemindData;
import com.example.bandtest.command.CommandManager;
import com.example.bandtest.db.DataHelper;
import com.example.bandtest.fragment.CustomDateDialogFragment;
import com.example.bandtest.fragment.TimePickerFragment;

import java.util.ArrayList;

public class ReminderActivity2 extends AppCompatActivity implements View.OnClickListener,TimePickerFragment.TimeListener, CustomDateDialogFragment.dateListener, CompoundButton.OnCheckedChangeListener {
    private final static String TAG=ReminderActivity2.class.getSimpleName();
    private Context mContext;
    private CommandManager manager;
    private Button selectTime,send;
    private EditText remindId;
    private Switch switch1;
    private TextView remind_data;

    private int month;
    private int dayOfMonth;
    private int hour;
    private int minute;
    private int id;
    private int sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder2);
        mContext = ReminderActivity2.this;
        manager = CommandManager.getInstance(this);
        initView();


    }

    public void initView() {
        selectTime = (Button) findViewById(R.id.selectTime);
        send = (Button) findViewById(R.id.send);
        remindId = (EditText) findViewById(R.id.remindId);
        remind_data = (TextView) findViewById(R.id.remind_data);
        switch1 = (Switch) findViewById(R.id.switch1);
        selectTime.setOnClickListener(this);
        send.setOnClickListener(this);
        switch1.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectTime:
                CustomDateDialogFragment customDateDialogFragment = new CustomDateDialogFragment();
                customDateDialogFragment.setListener(ReminderActivity2.this);
                customDateDialogFragment.show(getSupportFragmentManager(),"datePicker");



                break;

            case R.id.send:
                this.id = Integer.parseInt(remindId.getText().toString().trim());


                Log.i(TAG,"month"+month+"\n"
                +"dayOfMonth"+dayOfMonth+"\n"
                        +"hour"+hour+"\n"
                        +"minute"+minute+"\n"
                        +"id"+id+"\n"
                        +"switch"+sw

);


                DataHelper dataHelper = DataHelper.getsInstance(ReminderActivity2.this);


                dataHelper.inserRemindData(month+"-"+dayOfMonth+" "+hour+":"+minute,String.valueOf(id),String.valueOf(sw));
                ArrayList<RemindData> remindData = dataHelper.getRemindData();
                for (RemindData remindDatum : remindData) {
                    Log.i(TAG,remindDatum.toString());

                }
                remind_data.setText(remindData.toString());

                break;


            default:
                    break;
        }
    }

    @Override
    public void returnTime(int hour, int minute) {
        Log.i(TAG,"hour: "+hour +"  minute: "+minute);
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public void returnDate(int year, int month, int dayOfMonth) {
        Log.i(TAG,"year: "+year +"  month: "+month+"  dayOfMonth: "+dayOfMonth);
        this.month = month+1;
        this.dayOfMonth = dayOfMonth;

        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setTimeLisenter(ReminderActivity2.this);
        newFragment.show(getSupportFragmentManager(),"timePicker");
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        Log.i(TAG,"Switch State=" +isChecked);
        sw = isChecked? 1:0;
    }
}