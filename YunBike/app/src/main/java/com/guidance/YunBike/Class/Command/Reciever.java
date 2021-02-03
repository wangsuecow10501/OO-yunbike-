package com.guidance.YunBike.Class.Command;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class Reciever {
    //private Handler mHandler;
    // Our main handler that will receive callback notifications
    private ConnectedThread mConnectedThread;
    // bluetooth background worker thread to send and receive data
    private BluetoothSocket mBTSocket = null;
    // bi-directional client-to-client data path
    final String address = "00:18:E4:35:2A:A2";
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier

    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1;
    // used to identify adding bluetooth names
    private final static int MESSAGE_READ = 2;
    // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3;
    // used in bluetooth handler to identify message status
    private  String _recieveData = "";
    private BluetoothAdapter mBTAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothDevice device = mBTAdapter.getRemoteDevice(address);
    int bike_id;

    public Reciever(int bike_id)
    {
        this.bike_id=bike_id;
        new Thread() {
            public void run() {
                boolean fail = false;

                try {
                    mBTSocket = createBluetoothSocket(device);
                    mBTSocket.connect();
                    System.out.println("1111111111111111111111111");
                    //建立藍芽socket
                } catch (IOException e) {
                    fail = true;
                    //Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                }
                // Establish the Bluetooth socket connection.
                /*try {
                    mBTSocket.connect(); //建立藍芽連線
                    System.out.println("2222222222222222222222222");
                } catch (IOException e) {
                    try {
                        fail = true;
                        mBTSocket.close(); //關閉socket
                        System.out.println("3333333333333333333333");
                    } catch (IOException e2) {
                        //insert code to deal with this
                        //Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                }*/
                if (fail == false) {
                    //開啟執行緒用於傳輸及接收資料
                    mConnectedThread = new ConnectedThread(mBTSocket);
                    mConnectedThread.start();
                    System.out.println("44444444444444444444444444");
                }
            }
        }.start();
    }

    void open() {
        new Thread() {
            public void run() {
                _recieveData = ""; //清除上次收到的資料
                if(mConnectedThread != null) //First check to make sure thread created
                    mConnectedThread.write("1");
                try{mBTSocket.close();}catch(IOException e){};
            }
        }.start();
    }

    void lock() {
        new Thread() {
            public void run() {
                _recieveData = ""; //清除上次收到的資料
                if(mConnectedThread != null) //First check to make sure thread created
                    mConnectedThread.write("2");
                try{mBTSocket.close();}catch(IOException e){};
            }
        }.start();
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws
            IOException {
        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connection with BT device using UUID
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String input) {
            byte[] bytes = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }
    }
}
