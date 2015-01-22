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

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by marine on 22/01/15.
 */
public class DataStorage {

    private Context context;
    private String settingsFilename;
    private SharedPreferences settings;

    public DataStorage(Context activityContext, String filename) {
        context = activityContext;
        settingsFilename = filename;
        settings = context.getSharedPreferences(settingsFilename, Context.MODE_PRIVATE);
    }

    public void newSharedPreference(String key, String value) {
        settings.edit().putString(key, value).apply();
    }

    public void newSharedPreference(String key, int value) {
        settings.edit().putInt(key, value).apply();
    }

    public String getSharedPreference(String key) {
        return settings.getString(key, null);
    }

    public int getSharedPreferences(String key) {
        return settings.getInt(key,-1);
    }

    public void clearPreferences() {
        settings.edit().clear().apply();
    }
}