package mrmini.hold1e17.dk.mrmini;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;


import static android.content.ContentValues.TAG;


public class Scanner_Toy extends AppCompatActivity implements View.OnClickListener {

    Button startScan, write;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final int REQUEST_ENABLE_BT = 99;

    private BluetoothService.BluetoothConnectThread mBluetoothConnectThread;
    private BluetoothService.ConnectedThread mConnectedThread;
    private BluetoothService mBluetoothService = null;
    private BluetoothDevice remoteDevice = null;
    public String messageRead;

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
        write = (Button) findViewById(R.id.write);
        write.setOnClickListener(this);

        context = getApplicationContext();
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
        } else if (mBluetoothService == null) {
            setup();
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

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.d(TAG, "Name: " + deviceName + " Mac addresse: "+deviceHardwareAddress);

                if(deviceHardwareAddress.equals("20:17:01:11:58:81")){ //Adresse til MrMini
                    remoteDevice = mBluetoothAdapter.getRemoteDevice(deviceHardwareAddress);
                    mBluetoothService.connect(remoteDevice);

                }
            }
        }

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
                    messageRead = readMessage;
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
            }
        }
    };


    public void read() {

        if(messageRead )

        {
            int Message = 11;
            String readMessage;
            switch (Message) {
                case 1:  readMessage = "1";
                    break;
                case 2:  readMessage = "2";
                    break;
                case 3:  readMessage = "3";
                    break;
                case 4:  readMessage = "4";
                    break;
                case 5:  readMessage = "5";
                    break;
                case 6:  readMessage = "6";
                    break;
                case 7:  readMessage = "7";
                    break;
                case 8:  readMessage = "8";
                    break;
                case 9:  readMessage = "9";
                    break;
                case 10: readMessage = "10";
                    break;

            }
        }


        {

        }
    }


    @Override
    public void onClick(View v) {

        if (v == startScan) {
            if(remoteDevice != null){
                mBluetoothService.connect(remoteDevice);
            }
        }

        if (v == write) {
            mBluetoothService.mConnectedThread.write("3".getBytes());
        }

    }


}
