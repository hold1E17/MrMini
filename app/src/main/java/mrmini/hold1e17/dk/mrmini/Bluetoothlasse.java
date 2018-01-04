package com.lasse.bluetoothconnection.backend;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;



class ConnecterThread implements Runnable {

    //thread
    private volatile boolean shutdown = false;

    //Final variables
    private final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothDevice bluetoothDevice = null;
    private BluetoothSocket bluetoothSocket = null;


    private final InputStream inputStream;
    private final OutputStream outputStream;

    private Handler handlerToNotify;
    private HandlerStates handlerStates = new HandlerStates();


    public ConnecterThread(String MACAddress, Handler handlerToNotify) {
        this.handlerToNotify = handlerToNotify;

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(MACAddress);

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(myUUID);
            bluetoothSocket.connect();
            tmpIn = bluetoothSocket.getInputStream();
            tmpOut = bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            shutdown = true;
        }
        inputStream = tmpIn;
        outputStream = tmpOut;
    }

    /**
     * Continously listens for a message from the bluetoothconnection.
     */
    @Override
    public void run() {
        byte[] buffer = new byte[96];
        int bytes;

        //Listen for input
        while(!shutdown) {
            if(!bluetoothSocket.isConnected()) {
                handlerToNotify.obtainMessage(handlerStates.getHandlerStateDisconnected()).sendToTarget();
                shutdown = true;
                return;
            }



            try {
                bytes = inputStream.read(buffer);
                String readMSG = new String(buffer, 0, bytes);
                handlerToNotify.obtainMessage(handlerStates.getHandlerStateInformationReceived(), bytes, -1, readMSG).sendToTarget();
            }
            catch (IOException e) {
                Log.d("isConnected()", "" + bluetoothSocket.isConnected());
                shutdown = true;
                handlerToNotify.obtainMessage(handlerStates.getHandlerStateDisconnected()).sendToTarget();
                e.printStackTrace();
            }
        }

        //Entering this piece of code means that the thread shall shutdown.
        try {
            inputStream.close();
            outputStream.close();
            bluetoothSocket.close();
        } catch (IOException e) {

            e.printStackTrace();
        }



    }

    /**
     * Sends a string over the bluetooth connection.
     * @param input input
     * @throws IOException IOException
     */
    public void write(String input) throws IOException {
        byte[] msgBuffer = input.getBytes();
        if(outputStream!=null) {
            outputStream.write(msgBuffer);
        }
    }


    public void shutdown() {
        shutdown = true;

    }


    public boolean isAlive(){
        if(bluetoothSocket!=null && bluetoothSocket.isConnected() && shutdown==false) {
            Log.d("isAlive","true");
            return true;
        }
        Log.d("isAlive","false");
        return false;
    }

}