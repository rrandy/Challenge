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

package com.m2dl.challenge.core.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by kana on 22/01/15.
 */
public class ServerBluetooth extends Thread{

    private HashMap<String, String> data;
    private final BluetoothServerSocket blueServerSocket;
    private BluetoothAdapter blueAdapter;
    public ServerBluetooth(BluetoothAdapter blueAdapter_, String appName, String uuid) {
        this.blueAdapter=blueAdapter_;
        // On utilise un objet temporaire qui sera assign� plus tard � blueServerSocket car blueServerSocket est "final"
        BluetoothServerSocket tmp = null;
        try {
            // MON_UUID est l'UUID (comprenez identifiant serveur) de l'application. Cette valeur est n�cessaire c�t� client �galement !
            tmp = blueAdapter.listenUsingRfcommWithServiceRecord(appName, UUID.fromString(uuid));
        } catch (IOException e) { }
        blueServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket blueSocket = null;
        // On attend une erreur ou une connexion entrante
        while (true) {
            try {
                blueSocket = blueServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            // Si une connexion est accept�e
            if (blueSocket != null) {
                // On fait ce qu'on veut de la connexion (dans un thread s�par�), � vous de la cr�er
                manageConnectedSocket(blueSocket);
                try {
                    blueServerSocket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void manageConnectedSocket(BluetoothSocket blueSocket)
    {
        Log.i("BTserver", "manageConnectedSocked");
        try {

            InputStream inputStream = blueSocket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            data = (HashMap<String, String>) ois.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // On stoppe l'�coute des connexions et on tue le thread
    public void cancel() {
        try {
            blueServerSocket.close();
        } catch (IOException e) { }
    }

    public HashMap<String, String> getData(){
        return data;
    }
}
