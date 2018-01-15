package mrmini.hold1e17.dk.mrmini;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import static android.content.ContentValues.TAG;


public class Scanner_Toy extends AppCompatActivity implements View.OnClickListener {

    static Button startScan, connectbtn, afbrydbtn;
    static TextView statusText, statusTextScan;
    static ProgressBar progressBar;
    static ImageView checkImage, scannerImg;

    private static String savedCase;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final int REQUEST_ENABLE_BT = 99;
    private static boolean connected = false;

    private static BluetoothService mBluetoothService = null;
    private static BluetoothDevice remoteDevice = null;

    private static Context context;
    private static boolean scanning = false;

    /**
     * Name of the connected device
     */
    private static String mConnectedDeviceName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_toy);

        startScan = (Button) findViewById(R.id.startScan);
        startScan.setOnClickListener(this);
        connectbtn = (Button) findViewById(R.id.connectbtn);
        connectbtn.setOnClickListener(this);
        afbrydbtn = (Button) findViewById(R.id.afbrydbtn);
        afbrydbtn.setOnClickListener(this);

        statusText = (TextView) findViewById(R.id.statusView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        checkImage = (ImageView) findViewById(R.id.checkImage);
        scannerImg = (ImageView) findViewById(R.id.scanner_empty);
        statusTextScan = (TextView) findViewById(R.id.textStatusScan);

        context = getApplicationContext();

        updateUIStatus();

        if(savedInstanceState != null){
            savedCase = savedInstanceState.getString("savedCase");
            if(savedCase != null){
                read(savedCase);
            }
        }

        try{
            connected = savedInstanceState.getBoolean("connected");
            mConnectedDeviceName = savedInstanceState.getString("connectedDeviceName");
        } catch (Exception e){
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the bluetooth session
        }
        if (mBluetoothService == null) {
            setup();
            updateUIStatus();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                setup();
            } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_ikke_aktiv,
                            Toast.LENGTH_SHORT).show();
                    this.finish();

            }
        }
    }

    private void setup() {
        mBluetoothService = new BluetoothService(this, mHandler);
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        //TODO();
        updateUIStatus();

            search:
            if (pairedDevices.size() > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address
                    Log.d(TAG, "Name: " + deviceName + " Mac addresse: "+deviceHardwareAddress);

                    if(deviceHardwareAddress.equals("20:17:01:11:58:81")){ //Adresse til MrMini
                        remoteDevice = mBluetoothAdapter.getRemoteDevice(deviceHardwareAddress);
                        mBluetoothService.connect(remoteDevice);
                        break search;
                    }
                }
                // No device paired
                notPaired();
            } else {
                notPaired();
            }
        }

    private void notPaired(){
        Log.d(TAG, "MrMini not paired");
        Toast.makeText(this, R.string.bt_ikke_paired,
                Toast.LENGTH_LONG).show();
        this.finish();
    }

    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MessageConstants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    Log.d(TAG, "WRITE: " + writeMessage);
                     break;
                case BluetoothService.MessageConstants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    Log.d(TAG, "READ: " + readMessage);
                    read(readMessage);
                    //Toast.makeText(context, readMessage,
                      //      Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothService.MessageConstants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(BluetoothService.MessageConstants.DEVICE_NAME);
                    Toast.makeText(context, "Tilsluttet: "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothService.MessageConstants.MESSAGE_TOAST:
                    Toast.makeText(context, msg.getData().getString(BluetoothService.MessageConstants.TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothService.MessageConstants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            Log.d(TAG, "STATE: Connected to :" + mConnectedDeviceName);
                            updateUIStatus();
                            connected = true;
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            updateUIStatus();
                            Log.d(TAG, "STATE: Connecting");
                            break;
                        case BluetoothService.STATE_NONE:
                            Log.d(TAG, "STATE: Not connected");
                            updateUIStatus();
                            connected = false;
                            break;
                    }
                    break;
            }
        }
    };


    private static void updateUIStatus(){
        if(mBluetoothService != null){
            if(mBluetoothService.getState() == 1){
                statusText.setText("Tilslutter...");
                statusText.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                connectbtn.setVisibility(View.INVISIBLE);

                scannerImg.setVisibility(View.INVISIBLE);


            } else if(mBluetoothService.getState() == 2) {
                statusText.setText("");
                statusText.setVisibility(View.INVISIBLE);
                scannerImg.setVisibility(View.VISIBLE);

                progressBar.setVisibility(View.INVISIBLE);
                connectbtn.setVisibility(View.INVISIBLE);
                checkImage.setVisibility(View.VISIBLE);
                afbrydbtn.setVisibility(View.VISIBLE);

                read("2");


            } else if(mBluetoothService.getState() == 0) {
                statusText.setText("Enhed ikke tilsluttet");
                statusText.setVisibility(View.VISIBLE);
                scannerImg.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                checkImage.setVisibility(View.INVISIBLE);
                startScan.setVisibility(View.INVISIBLE);
                afbrydbtn.setVisibility(View.INVISIBLE);
                connectbtn.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v == startScan) {
            if(remoteDevice != null){
                startScanningActivity();
            }
        }
        if (v == connectbtn) {
            mBluetoothService.connect(remoteDevice);
        }
        if (v == afbrydbtn) {
            statusTextScan.setText("");
            mBluetoothService.mConnectedThread.cancel();
        }
    }

    public static void read(String messageRead) {

        if(scanning == true){
            Scanner_app_execute.endActivity();
            scanning = false;
        }

        switch (messageRead) {
            case "1":
                statusTextScan.setText("Dukken er blevet fjernet, og derfor afbrydes scanningen");
                scannerImg.setImageResource(R.drawable.scanner);
                savedCase = "1";
                startScan.setVisibility(View.INVISIBLE);
                break;
            case "2":
                statusTextScan.setText("Ingen dukke");
                scannerImg.setImageResource(R.drawable.scanner);
                savedCase = "2";
                startScan.setVisibility(View.INVISIBLE);
                break;
            case "3":
                statusTextScan.setText("Dukken er placeret korrekt");
                scannerImg.setImageResource(R.drawable.scanner_mand);
                savedCase = "3";
                startScan.setVisibility(View.VISIBLE);
                break;
            case "4":
                statusTextScan.setText("MR scanneren er ved at bevæge sig ind");
                scannerImg.setImageResource(R.drawable.scanner_mand);
                savedCase = "4";
                startScan.setVisibility(View.VISIBLE);
                break;
            case "5":
                statusTextScan.setText("MR scanneren er ved at bevæge sig ud");
                scannerImg.setImageResource(R.drawable.scanner_mand);
                savedCase = "5";
                startScan.setVisibility(View.INVISIBLE);
                break;
            case "6":
                statusTextScan.setText("Scanningen er igang");
                savedCase = "6";
                startScanningActivity();
                break;
            case "7":
                statusTextScan.setText("Dukken der er blevet placeret er en dreng");
                scannerImg.setImageResource(R.drawable.scanner_mand);
                savedCase = "7";
                break;
            case "8":
                statusTextScan.setText("Dukken der er blevet placeret er en pige");
                savedCase = "8";
                break;
            case "9":
                statusTextScan.setText("Dukken der er blevet placeret er en skildpadde");
                savedCase = "9";
                scannerImg.setImageResource(R.drawable.mrscanner_skildpadde);
                break;
        }
    }

    public static void writeToBluetooth(String msg){
        if(remoteDevice != null){
            mBluetoothService.mConnectedThread.write(msg.getBytes());
        }
    }

    private static void startScanningActivity(){
        scanning = true;

        Intent i = new Intent(context, Scanner_app_execute.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(mBluetoothService.mConnectedThread != null){
            mBluetoothService.mConnectedThread.cancel();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(mBluetoothService != null){
            outState.putString("savedCase", savedCase);
            outState.putString("connectedDeviceName", mConnectedDeviceName);
            if(mBluetoothService.getState() == 2){
                outState.putBoolean("connected", true);
            } else {
                outState.putBoolean("connected", false);
            }
        }

        super.onSaveInstanceState(outState);
    }
}