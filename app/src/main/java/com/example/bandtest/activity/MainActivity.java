package com.example.bandtest.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bandtest.App;
import com.example.bandtest.R;
import com.example.bandtest.command.CommandManager;
import com.example.bandtest.db.DataHelper;
import com.example.bandtest.db.DataInfo;
import com.example.bandtest.fragment.CustomDateDialogFragment;
import com.example.bandtest.service.BluetoothLeService;
import com.example.bandtest.utils.DataHandlerUtils;
import com.example.bandtest.utils.MyUtils;
import com.example.bandtest.utils.SPUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


public class MainActivity extends AppCompatActivity implements CustomDateDialogFragment.dateListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_SEARCH = 1;
    private TextView device_address, device_name, dateTxt, test_result, stepsTxt, distanceTxt, burntCalariesTxt;
    private String mDeviceName, mDeviceAddress;
    private List<String> list;
    private LocationManager locationManager;
    private Context mContext;
    private Button changeDateBtn, setReminderBtn, findBand, refreshData;
    public BluetoothLeService mBluetoothLeService;

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
            }
            // Automatically connects to the device upon successful start-up initialization.
            Log.d(TAG, "onServiceConnected");
            if (!TextUtils.isEmpty(mDeviceAddress)) {
                mBluetoothLeService.connect(mDeviceAddress);
                App.isConnecting = true;
                invalidateOptionsMenu();//显示正在连接 ...
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };
    private CommandManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        manager = CommandManager.getInstance(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        initView();

        mDeviceAddress = SPUtils.getString(mContext, SPUtils.DEVICE_ADDRESS, "");
        mDeviceName = SPUtils.getString(mContext, SPUtils.DEVICE_NAME, "");
        bindBleService();

        Log.i(TAG, DataInfo.CREATE_CURRENTDATA);
        DataHelper dataHelper = DataHelper.getsInstance(this);

        /*File file = new File("/data/data/" + this.getPackageName()
                + "/databases", DataInfo.DATABASE_NAME);
        if (!file.exists()) {
            //数据库不存在，初始化数据
          Toast.makeText(this,"数据库不存在",Toast.LENGTH_SHORT).show();
        } else {
            //数据库存在，更新数据
            Toast.makeText(this,"数据库存在",Toast.LENGTH_SHORT).show();

        }*/

    }

    private void bindBleService() {
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        device_address = (TextView) findViewById(R.id.device_address);
        device_name = (TextView) findViewById(R.id.device_name);
        test_result = (TextView) findViewById(R.id.test_result);
        dateTxt = (TextView) findViewById(R.id.dateTxt);
        stepsTxt = (TextView) findViewById(R.id.stepsTxt);
        distanceTxt = (TextView) findViewById(R.id.distanceTxt);
        burntCalariesTxt = (TextView) findViewById(R.id.burntCalariesTxt);

        changeDateBtn = (Button) findViewById(R.id.changeDateBtn);
        setReminderBtn = (Button) findViewById(R.id.setReminderBtn);
        findBand = (Button) findViewById(R.id.findBand);
        refreshData = (Button) findViewById(R.id.refreshData);

        changeDateBtn.setOnClickListener(this);
        setReminderBtn.setOnClickListener(this);
        findBand.setOnClickListener(this);
        refreshData.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        device_address.setText(mDeviceAddress);
        device_name.setText(mDeviceName);
        invalidateOptionsMenu();
    }

    private IntentFilter makeGattUpdateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (!App.mConnected) {
            menu.findItem(R.id.disconnect_ble).setVisible(false);
            menu.findItem(R.id.search_ble).setVisible(true);
        } else {
            menu.findItem(R.id.disconnect_ble).setVisible(true);
            menu.findItem(R.id.search_ble).setVisible(false);
        }
        if (!App.isConnecting) {
            menu.findItem(R.id.menu_refresh).setActionView(
                    null);
        } else {
            menu.findItem(R.id.menu_refresh).setActionView(
                    R.layout.actionbar_indeterminate_progress);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_ble:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (!MyUtils.isOpenLocationService(mContext)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(R.string.avaliable_gps);
                        builder.setTitle(R.string.notify);
                        builder.setPositiveButton(R.string.common_sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        }).show();

                    } else {
                        Intent intent = new Intent(MainActivity.this, DeviceScanActivity.class);
                        intent.putExtra("FROM_MAIN", 1);
                        startActivityForResult(intent, REQUEST_SEARCH);
                    }

                }

                finish();
                break;
            case R.id.disconnect_ble:
                mBluetoothLeService.disconnect();
                SPUtils.putString(mContext, SPUtils.DEVICE_ADDRESS, "");

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }


    private BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                App.isConnecting = false;
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                device_address.setText(R.string.disconnect2);
                device_name.setText("");
                invalidateOptionsMenu();
                mBluetoothLeService.close();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                Log.d(TAG, "ACTION_GATT_SERVICES_DISCOVERED: ");
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                final byte[] txValue = intent
                        .getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                Log.i(TAG, "Received command" + DataHandlerUtils.bytesToHexStr(txValue));
                List<Integer> datas = DataHandlerUtils.bytesToArrayList(txValue);
                Log.i(TAG, datas.toString());
                //RSSI
               /* if (datas.get(4) == 0XB5) {// [171, 0, 4, 255, 181, 128, 72]
                    Integer rssi = datas.get(6);
                    Log.d(TAG, "RSSI" + rssi);
                    test_result.setText("-" + rssi);
                }
                //按键测试
                if (datas.get(4) == 0XB6) {//[171, 0, 4, 255, 182, 128, 0]
                    Integer button = datas.get(6);
                    if (button == 0) {
                        test_result.setText(R.string.no_press);
                    } else if (button == 1) {
                        test_result.setText(R.string.press);
                    }
                }
                //充电 、电量
                if (datas.get(4) == 0X91) {//[171, 0, 5, 255, 145, 128, 0, 100]
                    Integer integer = datas.get(6);//是否充电
                    Integer integer1 = datas.get(7);//电量多少
                    if (integer == 0) {
                        test_result.setText(getString(R.string.no_charge) + getString(R.string.electricity) + integer1 + "%");
                    } else if (integer == 1) {
                        test_result.setText(getString(R.string.charging) + getString(R.string.electricity) + integer1 + "%");
                    }
                }
                //三轴传感器
                if (datas.get(4) == 0XB3) {//[171, 0, 5, 255, 179, 128, 1, 1]
                    Integer integer1 = datas.get(6);//通信是否正常
                    Integer integer2 = datas.get(7);//初始化是否成功
                    if (integer1 == 0) {
                        if (integer2 == 0) {
                            test_result.setText(getString(R.string.abnormal_communication) + getString(R.string.Initialization_unsuccessful));
                        } else if (integer2 == 1) {
                            test_result.setText(getString(R.string.abnormal_communication) + getString(R.string.Initialize_successfully));
                        }
                    } else if (integer1 == 1) {
                        if (integer2 == 0) {
                            test_result.setText(getString(R.string.communication_normal) + getString(R.string.Initialization_unsuccessful));
                        } else if (integer2 == 1) {
                            test_result.setText(getString(R.string.communication_normal) + getString(R.string.Initialize_successfully));
                        }
                    }
                }
                //心率传感器
                if (datas.get(4) == 0XB4) {//[171, 0, 4, 255, 180, 128, 1]
                    Integer integer = datas.get(6);
                    if (integer == 0) {
                        test_result.setText(getString(R.string.abnormal_communication));
                    } else if (integer == 1) {
                        test_result.setText(getString(R.string.communication_normal));
                    }
                }
                //测量心率
                if (datas.get(4) == 0x31) {//[171, 0, 5, 255, 49, 10, 0, 190]   [171, 0, 5, 255, 49, 10, 84, 48]
                    Integer integer = datas.get(6);
                    test_result.setText(String.valueOf(integer));
                }*/

                int steps, calories;
                float distance;
                //The first type of data------------------------------------------------------------
                if (datas.get(0) == 0xAB && datas.get(4) == 0x51 && datas.get(5) == 0x08) {
                    Log.d(TAG,"steps calories and sleep data current");

                    steps = (datas.get(6) << 16) + (datas.get(7) << 8) + datas.get(8);
                    distance = (steps * 0.7f)/1000;//If the user does not tell you his stride, by default he walked 0.7m every step
                    calories =(datas.get(9) << 16) + (datas.get(10) << 8) + datas.get(11);

                    BigDecimal bd = new BigDecimal((double) distance);
                    BigDecimal bigDecimal = bd.setScale(2, RoundingMode.HALF_UP);
                    float distance2 = bigDecimal.floatValue();
                    Log.i(TAG,"steps: " +steps +"  distance: "+distance2+"  calories: "+calories);
                    stepsTxt.setText("Steps -  "+String.valueOf(steps)+"stp");
                    distanceTxt.setText("Distance KM -  "+String.valueOf(distance2)+"km");
                    burntCalariesTxt.setText("Burnt calories -  "+String.valueOf(calories)+"kcal");
                }
                //The second type of data-----------------------------------------------------------
                if (datas.get(0) == 0xAB && datas.get(4) == 0x51 && datas.get(5) == 0x20){//Hourly
                    Log.d(TAG,"the steps, calories, heart rate, blood oxygen,blood pressure data from hourly measure");

                }
                if (datas.get(0) == 0){
                    Log.d(TAG,"second packet data from hourly measure");

                }

                //The third type of data------------------------------------------------------------
                if ((datas.get(0) == 0xAB && datas.get(4) == 0x51)){
                    if (datas.get(5) == 0x11){
                        Log.d(TAG,"the Heart rate data from band measure");

                    }else if (datas.get(5) == 0x12){
                        Log.d(TAG,"the Blood oxygen data from band measure");

                    }else if (datas.get(5) == 0x14){
                        Log.d(TAG,"the Blood pressure data from band measure");

                    }
                }

            }
        }
    };

    //Get Steps,Distance,Burnt Calaries from Band SDK of selected date
    @Override
    public void returnDate(String date) {
        dateTxt.setText(date);//Selected Date

        stepsTxt.setText("Steps -");//Get the steps from Band SDK
        distanceTxt.setText("Distance KM -  ");//Get the Distance from Band SDK
        burntCalariesTxt.setText("Burnt calaries - ");//Get the BurntCalaries from Band SDK

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeDateBtn:
                //Select date for getting Steps,Distance,Burnt Calaries from Band SDK
                CustomDateDialogFragment f = new CustomDateDialogFragment();
                f.setListener(MainActivity.this);
                f.show(getSupportFragmentManager(), "dialog");
                break;
            case R.id.setReminderBtn:
                //SetReminder Activity
                Intent intent = new Intent(mContext, ReminderActivity.class);
                startActivity(intent);
                break;
            case R.id.findBand:
                manager.findBand();
                break;
            case R.id.refreshData:
                manager.syncData(System.currentTimeMillis());
                break;
        }
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    @Override
    public void onBackPressed() {
        if (App.mConnected) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            showMessage("BandTest's running in background.\n             Disconnect to exit");
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.popup_title)
                    .setMessage(R.string.popup_message)
                    .setPositiveButton(R.string.popup_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.popup_no, null)
                    .show();
        }
    }
}