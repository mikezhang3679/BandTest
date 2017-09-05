package com.example.bandtest.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.bandtest.R;
import com.example.bandtest.command.CommandManager;

import java.util.ArrayList;
import java.util.List;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG=ReminderActivity.class.getSimpleName();
    private Context mContext;
    private EditText editTextReminderType,editTextStartDate_H,editTextStartDate_M,editTextTime;
    private Button removeBtn,addBtn;
    private CheckBox checkboxSunday,checkboxMonday,checkboxTuesday,checkboxWednesday,checkboxThursday,checkboxfriday,checkboxSaturday;
    private int id;
    private List<Integer> list;
    private CommandManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        mContext = ReminderActivity.this;
        manager = CommandManager.getInstance(this);
        initView();
    }

    public void initView() {
        editTextReminderType = (EditText) findViewById(R.id.editTextReminderType);
        editTextStartDate_H= (EditText) findViewById(R.id.editTextStartDate_H);
        editTextStartDate_M= (EditText) findViewById(R.id.editTextStartDate_M);
        editTextTime= (EditText) findViewById(R.id.editTextTime);

        checkboxSunday= (CheckBox) findViewById(R.id.checkboxSunday);
        checkboxMonday= (CheckBox) findViewById(R.id.checkboxMonday);
        checkboxTuesday= (CheckBox) findViewById(R.id.checkboxTuesday);
        checkboxWednesday= (CheckBox) findViewById(R.id.checkboxWednesday);
        checkboxThursday= (CheckBox) findViewById(R.id.checkboxThursday);
        checkboxfriday= (CheckBox) findViewById(R.id.checkboxfriday);
        checkboxSaturday= (CheckBox) findViewById(R.id.checkboxSaturday);

        addBtn = (Button) findViewById(R.id.addBtn);//Add Reminders for Prayers,Medicine and walking
        removeBtn = (Button) findViewById(R.id.removeBtn);//Remove Reminders for Prayers,Medicine and walking

        //OnClickListeners
        editTextReminderType.setOnClickListener(this);
        removeBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
    }

    //Select Reminders
    private void setReminderSpiner() {
        final String[] arr = getResources().getStringArray(R.array.setRemindertype);

        DialogInterface.OnClickListener expDialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editTextReminderType.setText(arr[which]);
                id=which;
            }
        };
        AlertDialog.Builder expbuilder = new AlertDialog.Builder(
                mContext);
        expbuilder.setTitle("Select Reminder");
        expbuilder.setItems(arr, expDialogListener);
        AlertDialog expdialog = expbuilder.create();
        expdialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editTextReminderType:
                //Hide keyboard
                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(editTextReminderType.getWindowToken(), 0);

                //Select Reminders for Prayers,Medicine and walking
                setReminderSpiner();
                break;

            case R.id.removeBtn:
                //Remove Reminders
                break;
            case R.id.addBtn:
                //Add Reminders
                Log.i(TAG,"id :"+id);
                String start_H = editTextStartDate_H.getText().toString();
                String start_M = editTextStartDate_M.getText().toString();
                String Time = editTextTime.getText().toString();
                if (TextUtils.isEmpty(start_H)||TextUtils.isEmpty(start_M)||TextUtils.isEmpty(Time)){
                    return;
                }

                int startH =Integer.parseInt(start_H) ;
                int startM = Integer.parseInt(start_M);
                int time = Integer.parseInt(Time);

                Log.i(TAG,"startH"+startH+"\nstartM"+startM+"\ntime"+time);
                boolean Sunday = checkboxSunday.isChecked();
                boolean Monday = checkboxMonday.isChecked();
                boolean Tuesday = checkboxTuesday.isChecked();
                boolean Wednesday = checkboxWednesday.isChecked();
                boolean Thursday = checkboxThursday.isChecked();
                boolean friday = checkboxfriday.isChecked();
                boolean Saturday = checkboxSaturday.isChecked();

                list=new ArrayList<>();
                list.add(Sunday? 1:0);
                list.add(Monday? 1:0);
                list.add(Tuesday? 1:0);
                list.add(Wednesday? 1:0);
                list.add(Thursday? 1:0);
                list.add(friday? 1:0);
                list.add(Saturday? 1:0);

                Log.i(TAG,"Sunday"+Sunday+"\nMonday"+Monday+"\nTuesday"+Tuesday+"\nWednesday"+Wednesday+"\nThursday"+Thursday+"\nfriday"+friday+"\nSaturday"+Saturday);
                Log.i(TAG,list.toString());
                manager.setRemind(id,startH,startM,time,list);


                break;
        }
    }
}