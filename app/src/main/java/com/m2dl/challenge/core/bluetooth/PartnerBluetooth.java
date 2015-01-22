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

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by kana on 22/01/15.
 */
public class PartnerBluetooth {
    private BluetoothDevice partner = null;
    public static PartnerBluetooth instance = new PartnerBluetooth();

    private PartnerBluetooth(){
    }

    public BluetoothDevice getPartnerBluetooth(){
        return  partner;
    }

    public void setPartnerBluetooth(BluetoothDevice partner){
        this.partner = partner;
    }

    public Set<BluetoothDevice> getPairBluetoothDevice(){
        return BluetoothAdapter.getDefaultAdapter().getBondedDevices();
    }

    public String getPartnerBluethoothName(){
        return partner.getName();
    }
}
