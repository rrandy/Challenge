/*
 * Copyright (c) 2015 Marine Carrara, Akana Mao, Randy Ratsimbazafy
 *
 * This file is part of Tracer c'est gagné.
 *
 * Tracer c'est gagné is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tracer c'est gagné is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Tracer c'est gagné.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.m2dl.challenge.core;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.widget.Toast;

import com.m2dl.challenge.core.bluetooth.ClientBluetooth;
import com.m2dl.challenge.core.bluetooth.ServerBluetooth;

import java.util.HashMap;
import java.util.Set;

public class DataBluetooth {

    private ServerBluetooth serverBluetooth;
    private ClientBluetooth clientBluetooth;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;

    public DataBluetooth(){
        bluetoothDevice = null;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                //Je cherche celui dont j'ai besoin (en fait les 2 tel avec lesquels j'ai test�
                if (device.getName().equals("HeroRAM1")) {
                    System.out.println("HeroRAM1");
                    bluetoothDevice=device;
                }
                if (device.getName().equals("GalaxyS")) {
                    System.out.println("HeroRAM3");
                    bluetoothDevice=device;
                }
            }
        }

    }

    public void sendDataByString(String key, String value){
        HashMap<String,String> data = new HashMap<String,String>();
        data.put(key, value);
        clientBluetooth = new ClientBluetooth(bluetoothDevice,bluetoothAdapter,"c065af87-b800-4bb3-a932-c4c130f2a50ddd");
        clientBluetooth.setData(data);
        clientBluetooth.start();
        Log.d("client send data", "key= "+key+", value="+value);
    }

    public String getData(String key){
        HashMap<String,String> data = null;
        Log.d("server start", "start");
        serverBluetooth = new ServerBluetooth(bluetoothAdapter,"challenge","c065af87-b800-4bb3-a932-c4c130f2a50ddd");
        serverBluetooth.start();
        while(data == null){
            data = serverBluetooth.getData();
        }
        Log.d("server receive data", "key= "+key+", value="+data.get(key));
        return data.get(key);
    }
}
