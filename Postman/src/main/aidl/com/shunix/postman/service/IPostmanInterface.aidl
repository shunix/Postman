package com.shunix.postman.service;

import android.bluetooth.BluetoothDevice;

interface IPostmanInterface {
    void connectDevice(in BluetoothDevice device);
}
