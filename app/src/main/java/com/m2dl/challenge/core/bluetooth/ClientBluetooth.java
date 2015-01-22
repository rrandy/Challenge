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
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.UUID;

public class ClientBluetooth extends Thread{
    private final BluetoothSocket blueSocket;
    private BluetoothAdapter blueAdapter;
    private HashMap<String, String> data;

    public ClientBluetooth(BluetoothDevice device, BluetoothAdapter blueAdapter_, String uuid) {
        // On utilise un objet temporaire car blueSocket et blueDevice sont "final"
        BluetoothSocket tmp = null;
        this.blueAdapter=blueAdapter_;

        try {

            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid));
        } catch (IOException e) { }
        blueSocket = tmp;
    }

    public void run() {
        // On annule la d�couverte des p�riph�riques (inutile puisqu'on est en train d'essayer de se connecter)
        blueAdapter.cancelDiscovery();

        try {
            // On se connecte. Cet appel est bloquant jusqu'� la r�ussite ou la lev�e d'une erreur
            blueSocket.connect();
        } catch (IOException connectException) {
            // Impossible de se connecter, on ferme la socket et on tue le thread
            try {
                blueSocket.close();
            } catch (IOException closeException) { }
            return;
        }

        // Utilisez la connexion (dans un thread s�par�) pour faire ce que vous voulez
        sendData();
        cancel();
    }

    public void setData(HashMap<String, String> data){
        this.data = data;
    }

    // Annule toute connexion en cours et tue le thread
    public void cancel() {
        try {
            blueSocket.close();
        } catch (IOException e) { }
    }

    public void sendData() {
        Log.i("BTClient", "ManageConnectedSocket");
        try {
            OutputStream outputStream = blueSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(data);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
