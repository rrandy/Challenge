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

package com.m2dl.challenge.challenge;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.m2dl.challenge.core.bluetooth.PartnerBluetooth;

import java.util.ArrayList;
import java.util.Set;

public class ActivityPartner extends Activity implements AdapterView.OnItemClickListener {

    private String[] pairBluetooth;
    private ArrayList<BluetoothDevice> bluetoothDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_partner);
        ListView pairBluetoothListView = (ListView) findViewById(R.id.pairBluetooth);


        bluetoothDevices = new ArrayList<BluetoothDevice>();
        Set<BluetoothDevice> pairBluetoothSet = PartnerBluetooth.instance.getPairBluetoothDevice();

        if (pairBluetoothSet.size() > 0) {
            int i = 0;
            pairBluetooth = new String[pairBluetoothSet.size()];
            for (BluetoothDevice device : pairBluetoothSet) {
               bluetoothDevices.add(device);
               pairBluetooth[i] = device.getName();
               Log.d("pairBluetooth",pairBluetooth[i]);
               i++;
            }
        }
        else{
            pairBluetooth = new String[]{"Votre téléphone n'a pas fait le pair"};
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, pairBluetooth);
        pairBluetoothListView.setAdapter(adapter);

        pairBluetoothListView.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PartnerBluetooth.instance.setPartnerBluetooth( bluetoothDevices.get(position) );
        Toast.makeText(this, PartnerBluetooth.instance.getPartnerBluetooth().getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ActivityPartner.this, ActivityMainMenu.class);
        startActivity(intent);
    }
}
