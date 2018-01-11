package mrmini.hold1e17.dk.mrmini;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
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

    Button startScan, connectbtn, afbrydbtn;
    TextView statusText;
    ProgressBar progressBar;
    ImageView checkImage;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final int REQUEST_ENABLE_BT = 99;
    private boolean connected = false;

    private static BluetoothService mBluetoothService = null;
    private BluetoothDevice remoteDevice = null;

    private Context context;

    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        context = getApplicationContext();

        try{
            connected = savedInstanceState.getBoolean("connected");
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

    private final Handler mHandler = new Handler() {
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
                    Toast.makeText(context, readMessage,
                            Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothService.MessageConstants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(BluetoothService.MessageConstants.DEVICE_NAME);
                    Toast.makeText(context, "Connected to "
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

    private void updateUIStatus(){
        if(mBluetoothService.getState() == 1){
            statusText.setText("Tilslutter...");
            progressBar.setVisibility(View.VISIBLE);
            connectbtn.setVisibility(View.INVISIBLE);


        } else if(mBluetoothService.getState() == 2) {
            statusText.setText("Tilsluttet: " + mConnectedDeviceName);
            progressBar.setVisibility(View.INVISIBLE);
            checkImage.setVisibility(View.VISIBLE);
            afbrydbtn.setVisibility(View.VISIBLE);
            startScan.setVisibility(View.VISIBLE);

        } else if(mBluetoothService.getState() == 0) {
            statusText.setText("Enhed ikke tilsluttet");
            progressBar.setVisibility(View.INVISIBLE);
            checkImage.setVisibility(View.INVISIBLE);
            startScan.setVisibility(View.INVISIBLE);
            afbrydbtn.setVisibility(View.INVISIBLE);
            connectbtn.setVisibility(View.VISIBLE);
        }
    }


    public void read(String messageRead) {

            switch (messageRead) {
                case "1":
                    System.out.println("Dukken er blevet fjernet, og derfor afbrydes scanningen");
                    break;
                case "2":
                    System.out.println("Ingen dukke");
                    break;
                case "3":
                    System.out.println("Dukken er placeret korrekt");
                    break;
                case "4":
                    System.out.println("MR scanneren er ved at bevæge sig ind");
                    break;
                case "5":
                    System.out.println("MR scanneren er ved at bevæge sig ud");
                    break;
                case "6":
                    System.out.println("Scanningen er igang");
                    break;
                case "7":
                    System.out.println("Dukken der er blevet placeret er en dreng");
                    break;
                case "8":
                    System.out.println("Dukken der er blevet placeret er en pige");
                    break;
                case "9":
                    System.out.println("Dukken der er blevet placeret er en skildpadde");
                    break;
            }
         }

    @Override
    public void onClick(View v) {

        if (v == startScan) {
            if(remoteDevice != null){
                mBluetoothService.mConnectedThread.write("3".getBytes());
            }
        }
        if (v == connectbtn) {
            mBluetoothService.connect(remoteDevice);
        }
        if (v == afbrydbtn) {
            mBluetoothService.mConnectedThread.cancel();
        }
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        if(mBluetoothService.mConnectedThread != null){
            mBluetoothService.mConnectedThread.cancel();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(mBluetoothService != null){
            if(mBluetoothService.getState() == 2){
                outState.putBoolean("connected", true);
            } else {
                outState.putBoolean("connected", false);
            }
        }

        super.onSaveInstanceState(outState);
    }
}
