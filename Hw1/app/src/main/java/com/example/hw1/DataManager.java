package com.example.hw1;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;

public class DataManager {

    public static final int MAX_LEN = 10;
    private Player[] players = new Player[MAX_LEN];
    private int curIndex = 0;

    public DataManager() { }

    public Player[] getPlayers() {

        return (players == null) ? new Player[MAX_LEN] : players;
    }

    public DataManager setPlayers(Player[] players) {
        this.players = players;
        return this;
    }

    private int getIndexForNewPlayer(Player player){
        int newScore = Integer.parseInt(player.getScore());
        for (int i = 0; i < curIndex; ++i) {
            int score = Integer.parseInt(players[i].getScore());
            if(newScore > score)
                return i;
        }
        return -1;
    }

    public void addPlayer(Player player){
        boolean isFreeSpace = curIndex < MAX_LEN;
        int indexToPlace = getIndexForNewPlayer(player);

        if(isFreeSpace){
            if(indexToPlace != -1){
                shiftArr(indexToPlace);
                players[indexToPlace] = player;
            }else{
                players[curIndex] = player;
            }
            curIndex++;
        }else if(indexToPlace != -1){
            shiftArr(indexToPlace);
            players[indexToPlace] = player;
        }
    }

    private void shiftArr(int i){
        for (int j = curIndex; j >= i ; --j) {
            if(j < MAX_LEN - 1){
                players[j+1] = players[j];
            }
        }
    }


}
