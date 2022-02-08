package com.example.partyquick;

import androidx.annotation.NonNull;

public class Time {

    private int hour;
    private int min;

    public Time(){
        hour = 0;
        min = 0;
    }

    public Time(@NonNull Time otherTime){
        setHour(otherTime.getHour())
                .setMin(otherTime.getMin());
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public Time setHour(int hour) {
        if(hour <24 && hour >=0)
            this.hour = hour;
        return this;
    }

    public Time setMin(int min) {
        if(min <60 && hour >=0)
            this.min = min;
        return this;
    }

    @Override
    public String toString() {
        String time = "";
        time += (hour < 10) ? ("0" + hour) : hour;
        time += ":";
        time += (min < 10) ? ("0" + min) : min;
        return time;
    }
}
