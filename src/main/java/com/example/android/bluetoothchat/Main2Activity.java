package com.example.android.bluetoothchat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.DatabaseClass;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.R.attr.typeface;


public final class Main2Activity extends BaseActivity {
    private static final String DEVICE_NAME = "DEVICE_NAME";
    private static final String LOG = "LOG";
    Records r;

    private static final SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss.SSS");

    private static String MSG_NOT_CONNECTED;
    private static String MSG_CONNECTING;
    private static String MSG_CONNECTED;

    private static DeviceConnector connector;
    private static BluetoothResponseHandler mHandler;
    private String deviceName;
TextView recieved_packet;




        @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);

            Button a = (Button)findViewById(R.id.a);
            Button b = (Button)findViewById(R.id.b);
            Button c = (Button)findViewById(R.id.c);
            Button d = (Button)findViewById(R.id.d);
            Button e = (Button)findViewById(R.id.e);
            Button f = (Button)findViewById(R.id.f);
            Button g = (Button)findViewById(R.id.g);
            Button h = (Button)findViewById(R.id.h);


            Typeface typeface= Typeface.createFromAsset(a.getContext().getAssets(), "fonts/OpenSansRegular.ttf");

            a.setTypeface(typeface);
            b.setTypeface(typeface);
            c.setTypeface(typeface);
            d.setTypeface(typeface);
            e.setTypeface(typeface);
            f.setTypeface(typeface);
            g.setTypeface(typeface);
            h.setTypeface(typeface);


            if (mHandler == null) mHandler = new BluetoothResponseHandler(this);
        else mHandler.setTarget(this);

        MSG_NOT_CONNECTED = getString(R.string.msg_not_connected);
        MSG_CONNECTING = getString(R.string.msg_connecting);
        MSG_CONNECTED = getString(R.string.msg_connected);

        recieved_packet= (TextView) findViewById(R.id.recieved_packet);



        if (isConnected() && (savedInstanceState != null)) {
            setDeviceName(savedInstanceState.getString(DEVICE_NAME));
        } else getSupportActionBar().setSubtitle(MSG_NOT_CONNECTED);

        r = new Records();

        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        String stime = time.format(cal1.getTime());

        r.setId(5);
        r.setDatenTime(stime);
        r.setValue("8");

        DatabaseClass insData = new DatabaseClass(this);
        insData.open();
        insData.insertdata(r);
        insData.close();

      /*  //dbhelper.onCreate(SQ);
        r = new Records();

        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        String stime = time.format(cal1.getTime());

        r.setId(1);
        r.setDatenTime(stime);
        r.setValue("10");

        dbhelper.insertdata(r);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat time1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        String stime1 = time1.format(cal1.getTime());
        r.setId(2);
        r.setDatenTime(stime1);
        r.setValue("12");

        //  Toast.makeText(this, stime, Toast.LENGTH_LONG).show();

        dbhelper.insertdata(r);

        Calendar cal2 = Calendar.getInstance();
        SimpleDateFormat time2 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        String stime2 = time2.format(cal1.getTime());
        r.setId(3);
        r.setDatenTime(stime2);
        r.setValue("7");

        Toast.makeText(this, stime, Toast.LENGTH_LONG).show();

        dbhelper.insertdata(r);
        */



    }

    public void view_graph(View v){
        Intent i = new Intent(this,display_graph.class);
        startActivity(i);
    }

    public void bargraph(View view) {
        Intent i = new Intent(this, BGraph.class);
        startActivity(i);
    }

    public void line_chart(View view) {
        Intent i = new Intent(this, lineChartClass.class);
        startActivity(i);
    }

    public void piechart(View view) {
        Intent i = new Intent(this, PieChartClass.class);
        this.startActivity(i);
    }

    public void update(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void displaydata(View view) {
        Intent i = new Intent(this, disData.class);
        startActivity(i);
    }

    public void mainpage(View v){
        Intent i = new Intent(this, Main3Activity.class);
        startActivity(i);
    }





    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DEVICE_NAME, deviceName);

    }
    public void connect(View v){
sendCommand("hello @ Banaoo\n");
    }

    @SuppressLint("DefaultLocale")

    private boolean isConnected() {
        return (connector != null) && (connector.getState() == DeviceConnector.STATE_CONNECTED);
    }
    private void stopConnection() {
        if (connector != null) {
            connector.stop();
            connector = null;
            deviceName = null;
        }
    }
    private void startDeviceListActivity() {
        stopConnection();
        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }

    @Override
    public boolean onSearchRequested() {
        if (super.isAdapterReady()) startDeviceListActivity();
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.device_control_activity, menu);
        final MenuItem bluetooth = menu.findItem(R.id.menu_search);
        if (bluetooth != null) bluetooth.setIcon(this.isConnected() ?
                R.drawable.ic_action_device_bluetooth_connected :
                R.drawable.ic_action_device_bluetooth);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_search:
                if (super.isAdapterReady()) {
                    if (isConnected()) stopConnection();
                    else startDeviceListActivity();
                } else {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // ============================================================================


    void setRecieveed_data( String data){
recieved_packet.setText(data);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    String address = data.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    BluetoothDevice device = btAdapter.getRemoteDevice(address);
                    if (super.isAdapterReady() && (connector == null)) setupConnector(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                super.pendingRequestEnableBt = false;
                if (resultCode != Activity.RESULT_OK) {
                    Utils.log("BT not enabled");
                }
                break;
        }
    }
    private void setupConnector(BluetoothDevice connectedDevice) {
        stopConnection();
        try {
            String emptyName = getString(R.string.empty_device_name);
            DeviceData data = new DeviceData(connectedDevice, emptyName);
            connector = new DeviceConnector(data, mHandler);
            connector.connect();
        } catch (IllegalArgumentException e) {
            Utils.log("setupConnector failed: " + e.getMessage());
        }
    }

    void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        getSupportActionBar().setSubtitle(deviceName);
    }
    public void sendCommand(String commandString) {



        byte[] command = commandString.getBytes();
        if (isConnected()) {
            connector.write(command);
        }
    }

    public void firstclick(View view) {
        Intent i = new Intent(this, Main4Activity.class);
        startActivity(i);

    }

    private class BluetoothResponseHandler extends Handler {
        private WeakReference<Main2Activity> mActivity;

        public BluetoothResponseHandler(Main2Activity activity) {
            mActivity = new WeakReference<Main2Activity>(activity);
        }

        public void setTarget(Main2Activity target) {
            mActivity.clear();
            mActivity = new WeakReference<Main2Activity>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            Main2Activity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case MESSAGE_STATE_CHANGE:

                        Utils.log("MESSAGE_STATE_CHANGE: " + msg.arg1);
                        final android.support.v7.app.ActionBar bar = activity.getSupportActionBar();
                        switch (msg.arg1) {
                            case DeviceConnector.STATE_CONNECTED:
                                if (bar != null) {
                                    bar.setSubtitle(MSG_CONNECTED);
                                }
                                break;
                            case DeviceConnector.STATE_CONNECTING:
                                if (bar != null) {
                                    bar.setSubtitle(MSG_CONNECTING);
                                }
                                break;
                            case DeviceConnector.STATE_NONE:
                                if (bar != null) {
                                    bar.setSubtitle(MSG_NOT_CONNECTED);
                                }
                                break;
                        }
                        activity.invalidateOptionsMenu();
                        break;
                    case MESSAGE_READ:
                        final String readMessage = (String) msg.obj;
                       activity.setRecieveed_data(readMessage);
                        break;

                    case MESSAGE_DEVICE_NAME:
                        activity.setDeviceName((String) msg.obj);
                        break;

                    case MESSAGE_WRITE:
                        // stub
                        break;

                    case MESSAGE_TOAST:
                        // stub
                        break;
                }
            }
        }
    }
    // ==========================================================================
}














