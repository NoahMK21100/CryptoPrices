package org.example.model;

import java.util.ArrayList;

public class CoinDataList {
    /*
    - Gives the list of CoinData objs with a timestamp
     */
    private ArrayList<CoinData> data;
    private long timestamp;

    public ArrayList<CoinData> getData() {
        return data;
    }

    public void setData(ArrayList<CoinData> data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
