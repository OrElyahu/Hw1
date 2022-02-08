package com.example.partyquick;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class Date {

    private final int[] DAYS = {31,28,31,30,31,30,31,31,30,31,30,31};
    private final String[] MONTHS = {"January", "February", "March", "April", "May", "June",
                                    "July", "August", "September", "October", "November", "December"};

    private int day;
    private int month;
    private int year;
    private Time time;

    public Date(){
        day = 1;
        month = 1;
        year = 2022;
        time = new Time();
    }

    public Date(@NonNull Date otherDate){
        setTime(otherDate.getTime())
                .setYear(otherDate.getYear())
                .setMonth(otherDate.getMonth())
                .setDay(otherDate.getDay());
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public Time getTime() {
        return time;
    }

    public Date setDay(int day) {
        if(day > 0 && day <= DAYS[month-1])
            this.day = day;
        return this;
    }

    public Date setMonth(int month) {
        if(month <= 12 && month > 0)
            this.month = month;
        return this;
    }

    public Date setYear(int year) {
        if(year >= 2022)
            this.year = year;
        return this;
    }

    public Date setTime(Time time) {
        this.time = new Time(time);
        return this;
    }

    @Override
    public String toString() {
        String date = "";
        date += (day < 10) ? ("0" + String.valueOf(day)) : String.valueOf(day);
        date += " ";
        date += MONTHS[month-1];
        date += " ";
        date += String.valueOf(year);
        date += " at ";
        date += time.toString();

        return date;
    }
}
