package com.example.bandtest.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.bandtest.R;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private EditText editTextReminderType,editTextStartDate,editTextEndDate,editTextTime;
    private Button removeBtn,addBtn;
    private CheckBox checkboxSunday,checkboxMonday,checkboxTuesday,checkboxWednesday,checkboxThursday,checkboxfriday,checkboxSaturday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        mContext = ReminderActivity.this;
        initView();
    }

    public void initView() {
        editTextReminderType = (EditText) findViewById(R.id.editTextReminderType);
        editTextStartDate= (EditText) findViewById(R.id.editTextStartDate);
        editTextEndDate= (EditText) findViewById(R.id.editTextEndDate);
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

                break;
        }
    }
}