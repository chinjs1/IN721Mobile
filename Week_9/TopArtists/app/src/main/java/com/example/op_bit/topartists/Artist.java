package com.example.op_bit.topartists;

/**
 * Created by chinjs1 on 15/04/2017.
 */

public class Artist {

    private String name;
    private int listenerCount;

    public Artist(String name, int listenerCount) {

        this.name = name;
        this.listenerCount = listenerCount;
    }

    public int getListenerCount() {
        return listenerCount;
    }

    public void setListenerCount(int listenerCount) {
        this.listenerCount = listenerCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
