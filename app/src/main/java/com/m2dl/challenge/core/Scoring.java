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

import com.m2dl.challenge.challenge.R;

/**
 * Created by marine on 22/01/15.
 */
public class Scoring {
    DataStorage storage;

    public static final int SCORE_WIN = 1;
    public static final int SCORE_LOOSE = 0;
    public static final int PLAYER_1 = 100;
    public static final int PLAYER_2 = 200;

    public Scoring(Context context) {
        storage = new DataStorage(context, context.getResources().getString(R.string.preferencesFilename));

        // Initialize scores
        storage.newSharedPreference("myPhone",0);
        storage.newSharedPreference("otherPlayerPhone",0);
    }

    public void updatePlayerScore(int player, int winOrLoose, int totalNbStrokes, int strokesUsed) {
        String playerKey = "";
        switch(player) {
            case PLAYER_1:
                playerKey = "myPhone";
                break;
            case PLAYER_2:
                playerKey = "otherPlayerPhone";
                break;
            default:
                break;
        }

        if (!playerKey.equals("")) {
            int oldScore = storage.getSharedPreferences(playerKey);
            storage.newSharedPreference(playerKey, computeNewScore(winOrLoose,strokesUsed,totalNbStrokes,oldScore));
        }
    }

    private int computeNewScore(int winOrLoose, int nbStrokesUsed, int totalNumberStrokes, int oldScore) {
        switch (winOrLoose) {
            case SCORE_WIN:
                int pointsToAdd = (totalNumberStrokes - nbStrokesUsed) * 10 + 10;
                return oldScore + pointsToAdd;
            case SCORE_LOOSE:
                // Do nothing - Score does not change
                return oldScore;
            default:
                return -1;
        }
    }

    public int getScore(int player) {
        String playerKey = "";
        switch(player) {
            case PLAYER_1:
                playerKey = "myPhone";
                break;
            case PLAYER_2:
                playerKey = "otherPlayerPhone";
                break;
            default:
                break;
        }
        return storage.getSharedPreferences(playerKey);
    }

}
