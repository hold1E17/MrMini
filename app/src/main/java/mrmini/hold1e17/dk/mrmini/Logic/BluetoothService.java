package mrmini.hold1e17.dk.mrmini.Logic;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by christofferpiilmann on 02/01/2018.
 */

public class BluetoothService{

        private static final String TAG = "BluetoothDebug";

        private final BluetoothAdapter mAdapter;
        private final Handler mHandler; // handler that gets info from Bluetooth service

        public BluetoothConnectThread mConnectThread;
        public ConnectedThread mConnectedThread;

        private int mState = STATE_NONE;
        private int mNewState;


        // Constants that indicate the current connection state
        public static final int STATE_NONE = 0;       // we're doing nothing
        public static final int STATE_CONNECTING = 1; // now initiating an outgoing connection
        public static final int STATE_CONNECTED = 2;  // now connected to a remote device

    


    // Defines several constants used when transmitting messages between the
        // service and the UI.
        public interface MessageConstants {
            public static final int MESSAGE_WRITE = 0;
            public static final int MESSAGE_READ = 1;
            public static final int MESSAGE_DEVICE_NAME = 2;
            public static final int MESSAGE_TOAST = 3;
            public static final int MESSAGE_STATE_CHANGE = 4;


            public static final String DEVICE_NAME = "device_name";
            public static final String TOAST = "toast";


            // ... (Add other message types here as needed.)
        }

        public BluetoothService(Context context, Handler handler) {
            mAdapter = BluetoothAdapter.getDefaultAdapter();
            mHandler = handler;
        }


        public synchronized void updateUserInterfaceTitle() {
            mState = getState();
            Log.d(TAG, "updateUserInterfaceTitle() " + mNewState + " -> " + mState);
            mNewState = mState;

            // Give the new state to the Handler so the UI Activity can update
            mHandler.obtainMessage(MessageConstants.MESSAGE_STATE_CHANGE, mNewState, -1).sendToTarget();
        }

        /**
         * Return the current connection state.
         */
        public synchronized int getState() {
            return mState;
        }

        public synchronized void connect(BluetoothDevice device) {
            Log.d(TAG, "connect to: " + device);

            sendToastTilUI("Tilslutter...");

            // Cancel any thread currently running a connection
            if (mConnectedThread != null) {
                mConnectedThread.cancel();
                mConnectedThread = null;
            }

            // Start the thread to connect with the given device
            if(mConnectedThread == null){
                mConnectThread = new BluetoothConnectThread(device); //Kører constructor
                mConnectThread.start(); // Kører run() metoden
            }

            updateUserInterfaceTitle();
        }

        private void sendToastTilUI(String msg) {
            // Send a connecting message back to the activity.
            Message writeMsg = mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString("toast", msg);
            writeMsg.setData(bundle);
            mHandler.sendMessage(writeMsg);
        }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    public class BluetoothConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        public BluetoothConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
            mState = STATE_CONNECTING;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            mBluetoothAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                connectionFailed();
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            connected(mmSocket, mmDevice);
        }

        private void connectionFailed() {
            sendToastTilUI("Unable to connect device");

            mState = STATE_NONE;
            // Update UI title
            updateUserInterfaceTitle();
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }


    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     *
     * @param socket The BluetoothSocket on which the connection was made
     * @param device The BluetoothDevice that has been connected
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice
            device) {
        Log.d(TAG, "connected to device");
        // Start the thread to manage the connection and perform transmissions
        //TODO()
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();

        // Send the name of the connected device back to the UI Activity
        Message msg = mHandler.obtainMessage(MessageConstants.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(MessageConstants.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        updateUserInterfaceTitle();

    }

    /*
    VED FORBUNDET TIL SERVER
     */
    public class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
            // member streams are final.
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating input stream", e);
            }
            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating output stream", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;

            mState = STATE_CONNECTED;
        }

        public void run() {
            mmBuffer = new byte[1024];
            int numBytes; // bytes returned from read()
            Log.d(TAG, "ConnectedThread run()");
            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    // Read from the InputStream.
                    numBytes = mmInStream.read(mmBuffer);
                    // Send the obtained bytes to the UI activity.
                    Message readMsg = mHandler.obtainMessage(
                            MessageConstants.MESSAGE_READ, numBytes, -1,
                            mmBuffer);
                    readMsg.sendToTarget();
                } catch (IOException e) {
                    Log.d(TAG, "Input stream was disconnected", e);
                    connectionLost();
                    break;
                }
            }
        }

        // Call this from the main activity to send data to the remote device.
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);

                // Share the sent message with the UI activity.
                Message writtenMsg = mHandler.obtainMessage(
                        MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
                writtenMsg.sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);

                // Send a failure message back to the activity.
                Message writeErrorMsg =
                        mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
                writeErrorMsg.setData(bundle);
                mHandler.sendMessage(writeErrorMsg);
            }
        }

        // Call this method from the main activity to shut down the connection.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }

        private void connectionLost() {
            sendToastTilUI("Device connection was lost");

            mState = STATE_NONE;
            // Update UI title
            updateUserInterfaceTitle();

        }
    }
}
